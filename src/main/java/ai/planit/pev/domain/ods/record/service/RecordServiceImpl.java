package ai.planit.pev.domain.ods.record.service;

import ai.planit.idp.sdk.model.IdpLoginUser;
import ai.planit.pev.core.exception.BaseException;
import ai.planit.pev.core.exception.ErrorType;
import ai.planit.pev.domain.meta.record.service.MetaRecordService;
import ai.planit.pev.domain.ods.anesthesia.service.AnesthesiaService;
import ai.planit.pev.domain.ods.bedsore.service.BedsoreService;
import ai.planit.pev.domain.ods.checkout.service.CheckoutService;
import ai.planit.pev.domain.ods.dialysis.blood.service.BloodDialysisService;
import ai.planit.pev.domain.ods.dialysis.peritoneal.service.PeritonealDialysisService;
import ai.planit.pev.domain.ods.discharge.service.DischargeService;
import ai.planit.pev.domain.ods.execute.service.ExecuteService;
import ai.planit.pev.domain.ods.fall.service.FallService;
import ai.planit.pev.domain.ods.function.service.FunctionService;
import ai.planit.pev.domain.ods.inpatient.service.InpatientService;
import ai.planit.pev.domain.ods.medical.service.MedicalService;
import ai.planit.pev.domain.ods.note.service.NoteService;
import ai.planit.pev.domain.ods.observation.service.ObservationService;
import ai.planit.pev.domain.ods.order.service.OrderService;
import ai.planit.pev.domain.ods.pathology.service.PathologyService;
import ai.planit.pev.domain.ods.picture.service.PictureService;
import ai.planit.pev.domain.ods.record.constant.RecordTarget;
import ai.planit.pev.domain.ods.record.dao.RecordListDAO;
import ai.planit.pev.domain.ods.record.dto.Record;
import ai.planit.pev.domain.ods.specimen.service.SpecimenService;
import ai.planit.pev.domain.ods.status.service.StatusService;
import ai.planit.pev.domain.ods.transfer.service.TransferService;
import ai.planit.pev.strategy.chart.*;
import ai.planit.pev.strategy.chart.constant.ChartClassType;
import ai.planit.pev.strategy.chart.constant.ChartControlType;
import ai.planit.pev.strategy.chart.object.common.*;
import ai.planit.pev.strategy.chart.object.medical.MedicalReply;
import ai.planit.pev.strategy.chart.object.pathology.PathologyData;
import ai.planit.pev.strategy.chart.object.picture.PictureData;
import ai.planit.pev.utility.PevChartUtil;
import ai.planit.pev.utility.PevStringUtil;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecordServiceImpl implements RecordService {

    private final RecordListDAO recordListDAO;
    private final MetaRecordService metaRecordService;
    private final MedicalService medicalService;
    private final PathologyService pathologyService;
    private final PictureService pictureService;
    private final OrderService orderService;
    private final AnesthesiaService anesthesiaService;
    private final SpecimenService specimenService;
    private final ObservationService observationService;
    private final InpatientService inpatientService;
    private final ExecuteService executeService;
    private final FallService fallService;
    private final BedsoreService bedsoreService;
    private final CheckoutService checkoutService;
    private final DischargeService dischargeService;
    private final TransferService transferService;
    private final FunctionService functionService;
    private final StatusService statusService;
    private final BloodDialysisService bloodDialysisService;
    private final PeritonealDialysisService peritonealDialysisService;
    private final NoteService noteService;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Record.Response> getRecordList(HttpSession session, Record.Request request) {
        String pid = (String) session.getAttribute("pev-pid");

        // 세션에 저장된 환자병록번호가 없을 경우 예외 처리한다.
        if (PevStringUtil.isStringEmpty(pid)) {
            throw new BaseException(ErrorType.PID_NOT_FOUND_IN_SESSION);
        }

        request.setPtNo(pid);

        Gson gson = new Gson();
        String requestStr = gson.toJson(request, Record.Request.class);
        session.setAttribute("pev-record-request", requestStr);

        return getRecordListByTargets(request);
    }

    /**
     * 기록 종류별 기록 목록 조회
     *
     * @param request 조회할 기록 목록의 상세 조건
     * @return 데이터베이스에서 조회한 기록 목록
     */
    private List<Record.Response> getRecordListByTargets(Record.Request request) {
        List<Record.Response> recordList = new ArrayList<>();

        List<String> searchTargetList = Arrays.asList(request.getSearchTargets());

        // 진료기록
        List<String> medicalRecordTargets = searchTargetList
                .stream()
                .filter(target -> target.startsWith("D0"))
                .collect(Collectors.toList());

        if (!medicalRecordTargets.isEmpty()) {
            recordList.addAll(getMedicalRecordList(request, medicalRecordTargets));
        }

        // 처방
        if (searchTargetList.contains(RecordTarget.ORDER_RECORD.getType())) {
            recordList.addAll(recordListDAO.getOrderRecordList(request));
        }

        // 검사
        List<String> examRecordTargets = searchTargetList
                .stream()
                .filter(target -> target.startsWith(RecordTarget.EXAM_RECORD.getType()))
                .collect(Collectors.toList());

        if (!examRecordTargets.isEmpty()) {
            recordList.addAll(getExamRecordList(request, examRecordTargets));
        }

        // 간호기록
        List<String> nrRecordTargets = searchTargetList
                .stream()
                .filter(target -> target.startsWith(RecordTarget.NURS_RECORD.getType()))
                .collect(Collectors.toList());

        if (!nrRecordTargets.isEmpty()) {
            recordList.addAll(getNrRecordList(request, nrRecordTargets));
        }

        // 스캔자료
        if (searchTargetList.contains(RecordTarget.SCAN_RECORD.getType())) {
            recordList.addAll(recordListDAO.getScanRecordList(request));
        }

        // 조건에 따라 여러 기록을 조회하기 때문에 모든 조회가 끝난 후 한번에 정렬한다.
        recordList = recordList
                .stream()
                .sorted(Comparator
                        .comparing(Record.Response::getWritingDate).reversed()
                        .thenComparing(Record.Response::getRecordDetailType))
                .collect(Collectors.toList());

        return recordList;
    }

    /**
     * 진료기록 목록 조회
     *
     * @param request              조회할 기록 목록의 상세 조건
     * @param medicalRecordTargets 조회할 진료기록의 상세 정보
     * @return 데이터베이스에서 조회한 진료기록의 상세 목록
     */
    private List<Record.Response> getMedicalRecordList(Record.Request request, List<String> medicalRecordTargets) {
        List<Record.Response> medicalRecordList = new ArrayList<>();

        // 진료기록 - 수술기록
        if (medicalRecordTargets.contains(RecordTarget.MEDICAL_SURGERY.getType())) {
            String[] queryTargets = {RecordTarget.MEDICAL_SURGERY.getType()};
            request.setQueryTargets(queryTargets);

            medicalRecordList.addAll(recordListDAO.getSurgeryRecordList(request));
        }

        // 진료기록 - 퇴원기록
        if (medicalRecordTargets.contains(RecordTarget.MEDICAL_DISCHARGE.getType())) {
            String[] queryTargets = {RecordTarget.MEDICAL_DISCHARGE.getType()};
            request.setQueryTargets(queryTargets);

            medicalRecordList.addAll(recordListDAO.getDischargeRecordList(request));
        }

        // 진료기록 - 타과의뢰
        if (medicalRecordTargets.contains(RecordTarget.MEDICAL_REQUEST.getType())) {
            String[] queryTargets = {RecordTarget.MEDICAL_REQUEST.getType()};
            request.setQueryTargets(queryTargets);

            medicalRecordList.addAll(recordListDAO.getRequestRecordList(request));
        }

        // 진료기록 - 마취기록, 마취 전 평가
        if (medicalRecordTargets
                .contains(RecordTarget.MEDICAL_ANESTHESIA.getType())
                || medicalRecordTargets.contains(RecordTarget.MEDICAL_BEFORE_ANESTHESIA.getType())) {
            String[] queryTargets = {
                    RecordTarget.MEDICAL_ANESTHESIA.getType(),
                    RecordTarget.MEDICAL_BEFORE_ANESTHESIA.getType()
            };

            if (!medicalRecordTargets.contains(RecordTarget.MEDICAL_ANESTHESIA.getType())) {
                queryTargets = Arrays.stream(queryTargets)
                        .filter(target -> !target.equals(RecordTarget.MEDICAL_ANESTHESIA.getType()))
                        .toArray(String[]::new);
            }

            if (!medicalRecordTargets.contains(RecordTarget.MEDICAL_BEFORE_ANESTHESIA.getType())) {
                queryTargets = Arrays.stream(queryTargets)
                        .filter(target -> !target.equals(RecordTarget.MEDICAL_BEFORE_ANESTHESIA.getType()))
                        .toArray(String[]::new);
            }

            request.setQueryTargets(queryTargets);

            medicalRecordList.addAll(recordListDAO.getAnesthesiaRecordList(request));
        }

        // 진료기록 - 일반
        List<String> generalTypeList = medicalRecordTargets.stream()
                .filter(type -> !type.equals(RecordTarget.MEDICAL_SURGERY.getType()))
                .filter(type -> !type.equals(RecordTarget.MEDICAL_DISCHARGE.getType()))
                .filter(type -> !type.equals(RecordTarget.MEDICAL_REQUEST.getType()))
                .filter(type -> !type.equals(RecordTarget.MEDICAL_ANESTHESIA.getType()))
                .filter(type -> !type.equals(RecordTarget.MEDICAL_BEFORE_ANESTHESIA.getType()))
                .collect(Collectors.toList());

        if (!generalTypeList.isEmpty()) {
            String[] queryTargets = generalTypeList.toArray(new String[0]);
            request.setQueryTargets(queryTargets);

            medicalRecordList.addAll(recordListDAO.getMedicalRecordList(request));
        }

        return medicalRecordList;
    }

    /**
     * 검사기록 목록 조회
     *
     * @param request           조회할 기록 목록의 상세 조건
     * @param examRecordTargets 조회할 검사기록의 상세 정보
     * @return 데이터베이스에서 조회한 검사기록의 상세 목록
     */
    private List<Record.Response> getExamRecordList(Record.Request request, List<String> examRecordTargets) {
        List<Record.Response> examRecordList = new ArrayList<>();

        // 영상검사
        if (examRecordTargets.contains(RecordTarget.EXAM_PICTURE.getType())) {
            examRecordList.addAll(recordListDAO.getExamPictureRecordList(request));
        }

        // 병리검사
        if (examRecordTargets.contains(RecordTarget.EXAM_PATHOLOGY.getType())) {
            examRecordList.addAll(recordListDAO.getExamPathologyRecordList(request));
        }

        // 검체검사
        if (examRecordTargets.contains(RecordTarget.EXAM_SPECIMEN.getType())) {
            examRecordList.addAll(recordListDAO.getExamSpecimenRecordList(request));
        }

        // 기능검사
        if (examRecordTargets.contains(RecordTarget.EXAM_FUNCTION.getType())) {
            examRecordList.addAll(recordListDAO.getExamFunctionRecordList(request));
        }

        return examRecordList;
    }

    private List<Record.Response> getNrRecordList(Record.Request request, List<String> nrRecordTargets) {
        List<Record.Response> nrRecordList = new ArrayList<>();

        if (nrRecordTargets.contains(RecordTarget.NURS_OBSERVATION.getType())) {
            nrRecordList.addAll(recordListDAO.getNrObservationRecordList(request));
        }

        if (nrRecordTargets.contains(RecordTarget.NURS_INPATIENT.getType())) {
            nrRecordList.addAll(recordListDAO.getNrInpatientRecordList(request));
        }

        if (nrRecordTargets.contains(RecordTarget.NURS_EXECUTE.getType())) {
            nrRecordList.addAll(recordListDAO.getNrExecuteRecordList(request));
        }

        if (nrRecordTargets.contains(RecordTarget.NURS_FALL.getType())) {
            nrRecordList.addAll(recordListDAO.getNrFallRecordList(request));
        }

        if (nrRecordTargets.contains(RecordTarget.NURS_BEDSORE.getType())) {
            nrRecordList.addAll(recordListDAO.getNrBedsoreRecordList(request));
        }

        if (nrRecordTargets.contains(RecordTarget.NURS_BEDSORE_EVALUATION.getType())) {
            nrRecordList.addAll(recordListDAO.getNrBedsoreEvaluationRecordList(request));
        }

        if (nrRecordTargets.contains(RecordTarget.NURS_CHECKOUT.getType())) {
            nrRecordList.addAll(recordListDAO.getNrCheckoutRecordList(request));
        }

        if (nrRecordTargets.contains(RecordTarget.NURS_DISCHARGE.getType())) {
            nrRecordList.addAll(recordListDAO.getNrDischargeRecordList(request));
        }

        if (nrRecordTargets.contains(RecordTarget.NURS_TRANSFER.getType())) {
            nrRecordList.addAll(recordListDAO.getNrTransferRecordList(request));
        }

        if (nrRecordTargets.contains(RecordTarget.NURS_STATUS.getType())) {
            nrRecordList.addAll(recordListDAO.getNrStatusRecordList(request));
        }

        if (nrRecordTargets.contains(RecordTarget.NURS_BLOOD_DIALYSIS.getType())) {
            nrRecordList.addAll(recordListDAO.getNrBloodDialysisRecordList(request));
        }

        if (nrRecordTargets.contains(RecordTarget.NURS_PERITONEAL_DIALYSIS.getType())) {
            nrRecordList.addAll(recordListDAO.getNrPeritonealDialysisRecordList(request));
        }

        if (nrRecordTargets.contains(RecordTarget.NURS_NOTE.getType())) {
            nrRecordList.addAll(recordListDAO.getNrNoteRecordList(request));
        }

        return nrRecordList;
    }

    public Chart.Response getChart(HttpSession session, Chart.Request request) {
        ChartContext chartContext = new ChartContext();

        List<ChartElement> format = metaRecordService.getRecordFormatList(request.getRecord());
        Object dataSource = null;

        // 진료기록
        if (request.getRecord().getRecordType().equals(RecordTarget.MEDICAL_RECORD.getType())) {
            if (request.getRecord().getRecordDetailType().equals(RecordTarget.MEDICAL_ANESTHESIA.getType())
                    || request.getRecord().getRecordDetailType().equals(RecordTarget.MEDICAL_BEFORE_ANESTHESIA.getType()) ) {
                chartContext.setChartStrategy(new AnesthesiaRecordChartStrategy(request.getRecord().getRecordDetailType()));

                Record.Response anesthesiaRecord = anesthesiaService.getAnesthesiaRecord(request.getRecord().getRecordDetailType(), request.getRecord().getOpExptRegId());

                format = metaRecordService.getRecordFormatList(anesthesiaRecord);

                ChartElement opNmEntity = new ChartElement();

                opNmEntity.setSectionId(-99);
                opNmEntity.setId("anesthesia-record-op-nm-1");
                opNmEntity.setParentId("-1000");
                opNmEntity.setMdfmCpemNo("anesthesia-record-op-nm-1");
                opNmEntity.setClassType(ChartClassType.ENTITY);
                opNmEntity.setControlType(ChartControlType.LABEL);
                opNmEntity.setMaskingType(null);
                opNmEntity.setContent("수술명");
                opNmEntity.setDesc(null);
                opNmEntity.setStyle(null);

                format.add(opNmEntity);

                ChartElement opNmValue = new ChartElement();

                opNmValue.setSectionId(-99);
                opNmValue.setId("anesthesia-record-op-nm-1-0-1");
                opNmValue.setParentId("anesthesia-record-op-nm-1");
                opNmValue.setMdfmCpemNo("anesthesia-record-op-nm-1-0-1");
                opNmValue.setClassType(ChartClassType.VALUE);
                opNmValue.setControlType(ChartControlType.TEXT_BOX);
                opNmValue.setMaskingType(null);
                opNmValue.setContent(null);
                opNmValue.setDesc(null);
                opNmValue.setStyle(null);

                format.add(opNmValue);

                if (request.getRecord().getRecordDetailType().equals(RecordTarget.MEDICAL_ANESTHESIA.getType())) {
                    ChartElement stfNmEntity = new ChartElement();

                    stfNmEntity.setSectionId(-98);
                    stfNmEntity.setId("anesthesia-record-stf-nm-1");
                    stfNmEntity.setParentId("-1000");
                    stfNmEntity.setMdfmCpemNo("anesthesia-record-stf-nm-1");
                    stfNmEntity.setClassType(ChartClassType.ENTITY);
                    stfNmEntity.setControlType(ChartControlType.LABEL);
                    stfNmEntity.setMaskingType(null);
                    stfNmEntity.setContent("Surgeons");
                    stfNmEntity.setDesc(null);
                    stfNmEntity.setStyle(null);

                    format.add(stfNmEntity);

                    ChartElement stfNmValue = new ChartElement();

                    stfNmValue.setSectionId(-98);
                    stfNmValue.setId("anesthesia-record-stf-nm-1-0-1");
                    stfNmValue.setParentId("anesthesia-record-stf-nm-1");
                    stfNmValue.setMdfmCpemNo("anesthesia-record-stf-nm-1-0-1");
                    stfNmValue.setClassType(ChartClassType.VALUE);
                    stfNmValue.setControlType(ChartControlType.TEXT_BOX);
                    stfNmValue.setMaskingType(null);
                    stfNmValue.setContent(null);
                    stfNmValue.setDesc(null);
                    stfNmValue.setStyle(null);

                    format.add(stfNmValue);

                    ChartElement historyEntity = new ChartElement();

                    historyEntity.setSectionId(99);
                    historyEntity.setId("anesthesia-record-history-1");
                    historyEntity.setParentId("-1000");
                    historyEntity.setMdfmCpemNo("anesthesia-record-history-1");
                    historyEntity.setClassType(ChartClassType.ENTITY);
                    historyEntity.setControlType(ChartControlType.LABEL);
                    historyEntity.setMaskingType(null);
                    historyEntity.setContent("마취기록");
                    historyEntity.setDesc(null);
                    historyEntity.setStyle(null);

                    format.add(historyEntity);

                    ChartElement historyValue = new ChartElement();

                    historyValue.setSectionId(99);
                    historyValue.setId("anesthesia-record-history-1-0-1");
                    historyValue.setParentId("anesthesia-record-history-1");
                    historyValue.setMdfmCpemNo("anesthesia-record-history-1-0-1");
                    historyValue.setClassType(ChartClassType.VALUE);
                    historyValue.setControlType(ChartControlType.RICH_TEXT_BOX);
                    historyValue.setMaskingType(null);
                    historyValue.setContent(null);
                    historyValue.setDesc(null);
                    historyValue.setStyle(null);

                    format.add(historyValue);
                }

                dataSource = anesthesiaService.getAnesthesiaRecordData(request.getRecord().getRecordDetailType(), request.getRecord().getOpExptRegId());
            } else {
                chartContext.setChartStrategy(new MedicalChartStrategy());
                dataSource = medicalService.getMedicalData(request.getRecord());
            }
        }

        if (request.getRecord().getRecordDetailType().equals(RecordTarget.ORDER_RECORD.getType())) {
            chartContext.setChartStrategy(new OrderChartStrategy());
            dataSource = orderService.getOrderData(session.getAttribute("pev-pid").toString(), request.getRecord());
        }

        if (request.getRecord().getRecordType().equals(RecordTarget.EXAM_RECORD.getType())) {
            // 병리검사
            if (request.getRecord().getRecordDetailType().equals(RecordTarget.EXAM_PATHOLOGY.getType())) {
                chartContext.setChartStrategy(new PathologyChartStrategy());

                PathologyData.Request pathologyDataRequest = new PathologyData.Request();
                pathologyDataRequest.setPthlNo(request.getRecord().getExamKey());

                dataSource = pathologyService.getPathologyData(pathologyDataRequest);
            }

            // 영상검사
            if (request.getRecord().getRecordDetailType().equals(RecordTarget.EXAM_PICTURE.getType())) {
                chartContext.setChartStrategy(new PictureChartStrategy());

                PictureData.Request pictureDataRequest = new PictureData.Request();
                pictureDataRequest.setIptnNo(request.getRecord().getExamKey());

                dataSource = pictureService.getPictureData(pictureDataRequest);
            }

            // 검체검사
            if (request.getRecord().getRecordDetailType().equals(RecordTarget.EXAM_SPECIMEN.getType())) {
                chartContext.setChartStrategy(new SpecimenChartStrategy());
                dataSource = specimenService.getSpecimenData(session, request.getRecord());
            }

            // 기능검사
            if (request.getRecord().getRecordDetailType().equals(RecordTarget.MEDICAL_DEPARTMENT.getType())) {
                chartContext.setChartStrategy(new FunctionChartStrategy());
                dataSource = functionService.getFunctionData(request.getRecord().getKeyId());
            }
        }

        // 스캔자료
        if (request.getRecord().getRecordType().equals(RecordTarget.SCAN_RECORD.getType())) {
            chartContext.setChartStrategy(new ScanChartStrategy());
            dataSource = request.getRecord();
        }

        // 간호기록
        if (request.getRecord().getRecordType().equals(RecordTarget.NURS_RECORD.getType())) {
            // 임상관찰기록
            if (request.getRecord().getRecordDetailType().equals(RecordTarget.NURS_OBSERVATION.getType())) {
                chartContext.setChartStrategy(new ObservationChartStrategy());
                dataSource = observationService.getObservationData(session, request.getRecord());
            }
            // 입원간호정보
            if (request.getRecord().getRecordDetailType().equals(RecordTarget.NURS_INPATIENT.getType())) {
                chartContext.setChartStrategy(new InpatientChartStrategy());
                format = inpatientService.getInpatientFormat(request.getRecord());
                dataSource = null;
            }
            // 간호활동수행기록
            if (request.getRecord().getRecordDetailType().equals(RecordTarget.NURS_EXECUTE.getType())) {
                chartContext.setChartStrategy(new ExecuteChartStrategy());
                format = executeService.getNrExecuteFormat(request.getRecord());
                dataSource = null;
            }

            // 낙상위험도평가
            if (request.getRecord().getRecordDetailType().equals(RecordTarget.NURS_FALL.getType())) {
                chartContext.setChartStrategy(new FallChartStrategy());
                dataSource =  fallService.getFallData(request.getRecord().getKeyId());
            }

            // 욕창간호기록
            if (request.getRecord().getRecordDetailType().equals(RecordTarget.NURS_BEDSORE.getType())) {
                chartContext.setChartStrategy(new BedsoreChartStrategy());
                format = bedsoreService.getBedsoreFormat(request.getRecord().getKeyId());
                dataSource = null;
            }

            // 욕창위험도평가
            if (request.getRecord().getRecordDetailType().equals(RecordTarget.NURS_BEDSORE_EVALUATION.getType())) {
                chartContext.setChartStrategy(new BedsoreEvaluationChartStrategy());
                dataSource = bedsoreService.getBedsoreEvaluationData(request.getRecord().getKeyId());
            }

            // 퇴실간호기록
            if (request.getRecord().getRecordDetailType().equals(RecordTarget.NURS_CHECKOUT.getType())) {
                chartContext.setChartStrategy(new CheckoutChartStrategy());
                dataSource = checkoutService.getNrCheckoutData(request.getRecord().getKeyId());
            }

            // 퇴원간호기록
            if (request.getRecord().getRecordDetailType().equals(RecordTarget.NURS_DISCHARGE.getType())) {
                chartContext.setChartStrategy(new DischargeChartStrategy());
                dataSource = dischargeService.getNrDischargeData(request.getRecord().getKeyId());
            }

            // 전과전동간호기록
            if (request.getRecord().getRecordDetailType().equals(RecordTarget.NURS_TRANSFER.getType())) {
                chartContext.setChartStrategy(new TransferChartStrategy());
                dataSource = transferService.getNrTransferData(request.getRecord().getKeyId());
            }

            // 수술전상태확인
            if (request.getRecord().getRecordDetailType().equals(RecordTarget.NURS_STATUS.getType())) {
                chartContext.setChartStrategy(new StatusChartStrategy());
                dataSource = statusService.getNrStatusValueList(request.getRecord().getKeyId());
            }

            // 혈액투석간호기록
            if (request.getRecord().getRecordDetailType().equals(RecordTarget.NURS_BLOOD_DIALYSIS.getType())) {
                chartContext.setChartStrategy(new BloodDialysisChartStrategy());
                dataSource = bloodDialysisService.getBloodDialysisData(request.getRecord().getKeyId());
            }

            // 복막투석간호기록
            if (request.getRecord().getRecordDetailType().equals(RecordTarget.NURS_PERITONEAL_DIALYSIS.getType())) {
                chartContext.setChartStrategy(new PeritionealDialysisChartStrategy());
                dataSource = peritonealDialysisService.getPeritonealDialysisData(request.getRecord().getKeyId());
            }

            // 간호일지
            if (request.getRecord().getRecordDetailType().equals(RecordTarget.NURS_NOTE.getType())) {
                chartContext.setChartStrategy(new NoteChartStrategy());
                dataSource = noteService.getNoteData(request.getRecord().getKeyId());
            }
        }

        List<ChartElement> data = chartContext.getChartStrategy().getData(format, dataSource);

        boolean applyStyle = PevChartUtil.applyStyle(request.getRecord().getRecordDetailType());

        List<ChartStyleSection> style = new ArrayList<>();

        if (applyStyle) {
            ChartStyleXml.Request xmlRequest = new ChartStyleXml.Request();
            xmlRequest.setMdfmClsCd(request.getRecord().getRecordDetailType());
            xmlRequest.setMdfmId(request.getRecord().getMdfmId());
            xmlRequest.setMdfmFomSeq(request.getRecord().getMdfmFomSeq());

            style = medicalService.getChartStyleSections(xmlRequest);
        }

        String pid = (String) session.getAttribute("pev-pid");

        // 세션에 저장된 환자병록번호가 없을 경우 예외 처리한다.
        if (PevStringUtil.isStringEmpty(pid)) {
            throw new BaseException(ErrorType.PID_NOT_FOUND_IN_SESSION);
        }

        boolean withOrigin = false;
        String userStr = (String) session.getAttribute("pev-user");

        if (userStr != null) {
            Gson gson = new Gson();
            IdpLoginUser idpLoginUser = gson.fromJson(userStr, IdpLoginUser.class);
            withOrigin = idpLoginUser.getAuthCd().equals("S");
        }

        ChartData chartData = new ChartData(pid, data, withOrigin);
        if (request.getMaskingYn().equals("Y")) chartData = chartContext.getMaskedData(chartData);

        return chartContext.getChart(format, chartData.getValues(), style, applyStyle);
    }

    @Override
    public MedicalReply.Response getChartReply(HttpSession session, MedicalReply.Request request) {
        MedicalReply.Response medicalReply = new MedicalReply.Response();

        Record.Response record = medicalService.getMedicalReplyRecord(request);

        if (record == null) {
            medicalReply.setReplyYn("N");
            return medicalReply;
        }

        Chart.Request chartRequest = new Chart.Request();

        chartRequest.setMaskingYn(request.getMaskingYn());
        chartRequest.setRecord(record);

        medicalReply.setReplyYn("Y");
        medicalReply.setChart(getChart(session, chartRequest));
        medicalReply.setRecord(record);

        return medicalReply;
    }

    @Override
    public List<Chart.Response> getFunctionChart(HttpSession session, Chart.Request request) {
        List<Chart.Response> functionChartList = new ArrayList<>();

        List<Record.Response> recordList = functionService.getFunctionRecordList(request.getRecord().getKeyId());

        for (Record.Response record : recordList) {
            Chart.Request chartRequest = new Chart.Request();
            chartRequest.setMaskingYn(request.getMaskingYn());
            chartRequest.setRecord(record);

            functionChartList.add(getChart(session, chartRequest));
        }

        return functionChartList;
    }
}
