package ai.planit.pev.domain.ods.record.service;

import ai.planit.idp.sdk.model.IdpLoginUser;
import ai.planit.pev.domain.image.dto.ImageDTO;
import ai.planit.pev.domain.image.service.ImageService;
import ai.planit.pev.domain.meta.event.dao.EventDAO;
import ai.planit.pev.domain.meta.event.dto.Event;
import ai.planit.pev.domain.meta.record.service.MetaRecordService;
import ai.planit.pev.domain.ods.anesthesia.service.AnesthesiaService;
import ai.planit.pev.domain.ods.bedsore.service.BedsoreService;
import ai.planit.pev.domain.ods.checkout.service.CheckoutService;
import ai.planit.pev.domain.ods.cpr.service.CprService;
import ai.planit.pev.domain.ods.dialysis.blood.service.BloodDialysisService;
import ai.planit.pev.domain.ods.dialysis.peritoneal.service.PeritonealDialysisService;
import ai.planit.pev.domain.ods.discharge.service.DischargeService;
import ai.planit.pev.domain.ods.execute.service.ExecuteService;
import ai.planit.pev.domain.ods.fall.service.FallService;
import ai.planit.pev.domain.ods.function.service.FunctionService;
import ai.planit.pev.domain.ods.inpatient.service.InpatientService;
import ai.planit.pev.domain.ods.medical.dto.MedicalImage;
import ai.planit.pev.domain.ods.medical.service.MedicalService;
import ai.planit.pev.domain.ods.note.dao.NoteDAO;
import ai.planit.pev.domain.ods.note.service.NoteService;
import ai.planit.pev.domain.ods.observation.service.ObservationService;
import ai.planit.pev.domain.ods.order.service.OrderService;
import ai.planit.pev.domain.ods.pathology.service.PathologyService;
import ai.planit.pev.domain.ods.picture.service.PictureService;
import ai.planit.pev.domain.ods.record.dao.RecordListDAO;
import ai.planit.pev.domain.ods.record.dto.Record;
import ai.planit.pev.domain.ods.specimen.service.SpecimenService;
import ai.planit.pev.domain.ods.status.service.StatusService;
import ai.planit.pev.domain.ods.transfer.service.TransferService;
import ai.planit.pev.strategy.chart.*;
import ai.planit.pev.strategy.chart.constant.ChartClassType;
import ai.planit.pev.strategy.chart.constant.ChartControlType;
import ai.planit.pev.strategy.chart.object.common.*;
import ai.planit.pev.strategy.chart.object.medical.MedicalData;
import ai.planit.pev.strategy.chart.object.medical.MedicalReply;
import ai.planit.pev.strategy.chart.object.medical.SurgeryData;
import ai.planit.pev.strategy.chart.object.note.NoteData;
import ai.planit.pev.strategy.chart.object.note.NoteValue;
import ai.planit.pev.strategy.chart.object.pathology.PathologyData;
import ai.planit.pev.strategy.chart.object.picture.PictureData;
import ai.planit.pev.utility.PevChartUtil;
import ai.planit.pev.utility.PevDocumentRenderUtil;
import ai.planit.pev.utility.SessionUtil;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

import static ai.planit.pev.domain.ods.record.constant.RecordTarget.*;

@Service
@RequiredArgsConstructor
@Slf4j
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
    private final CprService cprService;
    private final EventDAO eventDAO;
    private final NoteDAO noteDAO;
    private final ImageService imageService;

    private static final List<String> XML_RECORD_LIST = List.of(MEDICAL_DEPARTMENT.getType(), CERTIFICATE_REQUEST.getType());

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Record.Response> getRecordList(HttpSession session, Record.Request request) {
        String pid = SessionUtil.getPid(session);
        request.setPtNo(pid);

        IdpLoginUser loginUser = SessionUtil.getLoginUser(session);

        // 이벤트 로그
        Event event = new Event(request);
        event.setStfNo(loginUser.getStfNo());
        event.setStfNm(loginUser.getStfNm());
        eventDAO.insertEvent(event);

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
        List<String> medical = searchTargetList
                .stream()
                .filter(target -> target.startsWith("D0"))
                .collect(Collectors.toList());

        if (!medical.isEmpty()) {
            recordList.addAll(getMedicalRecordList(request, medical));
        }

        // 처방
        if (searchTargetList.contains(ORDER_RECORD.getType())) {
            recordList.addAll(recordListDAO.getOrderRecordList(request));
        }

        // 검사
        List<String> exam = searchTargetList
                .stream()
                .filter(target -> target.startsWith(EXAM_RECORD.getType()))
                .collect(Collectors.toList());

        if (!exam.isEmpty()) {
            recordList.addAll(getExamRecordList(request, exam));
        }

        // 간호기록
        List<String> nr = searchTargetList
                .stream()
                .filter(target -> target.startsWith(NURS_RECORD.getType()))
                .collect(Collectors.toList());

        if (!nr.isEmpty()) {
            recordList.addAll(getNrRecordList(request, nr));
        }

        // 스캔자료
        if (searchTargetList.contains(SCAN_RECORD.getType())) {
            recordList.addAll(recordListDAO.getScanRecordList(request));
        }

        // 특성화 기록
        List<String> cr = searchTargetList
                .stream()
                .filter(target -> target.startsWith("CR"))
                .collect(Collectors.toList());

        if (!cr.isEmpty()) {
            recordList.addAll(getCrRecordList(request, cr));
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
     * @param medical 조회할 진료기록의 상세 정보
     * @return 데이터베이스에서 조회한 진료기록의 상세 목록
     */
    private List<Record.Response> getMedicalRecordList(Record.Request request, List<String> medical) {
        List<Record.Response> medicalRecordList = new ArrayList<>();

        // 진료기록 - 수술기록
        if (medical.contains(MEDICAL_SURGERY.getType())) {
            String[] queryTargets = {MEDICAL_SURGERY.getType()};
            request.setQueryTargets(queryTargets);

            medicalRecordList.addAll(recordListDAO.getSurgeryRecordList(request));
        }

        // 진료기록 - 퇴원기록
        if (medical.contains(MEDICAL_DISCHARGE.getType())) {
            String[] queryTargets = {MEDICAL_DISCHARGE.getType()};
            request.setQueryTargets(queryTargets);

            medicalRecordList.addAll(recordListDAO.getDischargeRecordList(request));
        }

        // 진료기록 - 타과의뢰
        if (medical.contains(MEDICAL_REQUEST.getType())) {
            String[] queryTargets = {MEDICAL_REQUEST.getType()};
            request.setQueryTargets(queryTargets);

            medicalRecordList.addAll(recordListDAO.getRequestRecordList(request));
        }

        // 진료기록 - 마취기록, 마취 전 평가
        if (medical.contains(MEDICAL_ANESTHESIA.getType()) ||
                medical.contains(MEDICAL_BEFORE_ANESTHESIA.getType())) {
            String[] queryTargets = {
                    MEDICAL_ANESTHESIA.getType(),
                    MEDICAL_BEFORE_ANESTHESIA.getType()
            };

            if (!medical.contains(MEDICAL_ANESTHESIA.getType())) {
                queryTargets = Arrays.stream(queryTargets)
                        .filter(target -> !target.equals(MEDICAL_ANESTHESIA.getType()))
                        .toArray(String[]::new);
            }

            if (!medical.contains(MEDICAL_BEFORE_ANESTHESIA.getType())) {
                queryTargets = Arrays.stream(queryTargets)
                        .filter(target -> !target.equals(MEDICAL_BEFORE_ANESTHESIA.getType()))
                        .toArray(String[]::new);
            }

            request.setQueryTargets(queryTargets);

            medicalRecordList.addAll(recordListDAO.getAnesthesiaRecordList(request));
        }

        // 진료기록 - 일반
        List<String> generalTypeList = medical.stream()
                .filter(type -> !type.equals(MEDICAL_SURGERY.getType()))
                .filter(type -> !type.equals(MEDICAL_DISCHARGE.getType()))
                .filter(type -> !type.equals(MEDICAL_REQUEST.getType()))
                .filter(type -> !type.equals(MEDICAL_ANESTHESIA.getType()))
                .filter(type -> !type.equals(MEDICAL_BEFORE_ANESTHESIA.getType()))
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
     * @param exam 조회할 검사기록의 상세 정보
     * @return 데이터베이스에서 조회한 검사기록의 상세 목록
     */
    private List<Record.Response> getExamRecordList(Record.Request request, List<String> exam) {
        List<Record.Response> examRecordList = new ArrayList<>();

        // 영상검사
        if (exam.contains(EXAM_PICTURE.getType())) {
            examRecordList.addAll(recordListDAO.getExamPictureRecordList(request));
        }

        // 병리검사
        if (exam.contains(EXAM_PATHOLOGY.getType())) {
            examRecordList.addAll(recordListDAO.getExamPathologyRecordList(request));
        }

        // 검체검사
        if (exam.contains(EXAM_SPECIMEN.getType())) {
            examRecordList.addAll(recordListDAO.getExamSpecimenRecordList(request));
        }

        // 기능검사
        if (exam.contains(EXAM_FUNCTION.getType())) {
            examRecordList.addAll(recordListDAO.getExamFunctionRecordList(request));
        }

        return examRecordList;
    }

    private List<Record.Response> getNrRecordList(Record.Request request, List<String> nr) {
        List<Record.Response> nrRecordList = new ArrayList<>();

        if (nr.contains(NURS_OBSERVATION.getType())) {
            nrRecordList.addAll(recordListDAO.getNrObservationRecordList(request));
        }

        if (nr.contains(NURS_INPATIENT.getType())) {
            nrRecordList.addAll(recordListDAO.getNrInpatientRecordList(request));
        }

        if (nr.contains(NURS_EXECUTE.getType())) {
            nrRecordList.addAll(recordListDAO.getNrExecuteRecordList(request));
        }

        if (nr.contains(NURS_FALL.getType())) {
            nrRecordList.addAll(recordListDAO.getNrFallRecordList(request));
        }

        if (nr.contains(NURS_BEDSORE.getType())) {
            nrRecordList.addAll(recordListDAO.getNrBedsoreRecordList(request));
        }

        if (nr.contains(NURS_BEDSORE_EVALUATION.getType())) {
            nrRecordList.addAll(recordListDAO.getNrBedsoreEvaluationRecordList(request));
        }

        if (nr.contains(NURS_CHECKOUT.getType())) {
            nrRecordList.addAll(recordListDAO.getNrCheckoutRecordList(request));
        }

        if (nr.contains(NURS_DISCHARGE.getType())) {
            nrRecordList.addAll(recordListDAO.getNrDischargeRecordList(request));
        }

        if (nr.contains(NURS_TRANSFER.getType())) {
            nrRecordList.addAll(recordListDAO.getNrTransferRecordList(request));
        }

        if (nr.contains(NURS_STATUS.getType())) {
            nrRecordList.addAll(recordListDAO.getNrStatusRecordList(request));
        }

        if (nr.contains(NURS_BLOOD_DIALYSIS.getType())) {
            nrRecordList.addAll(recordListDAO.getNrBloodDialysisRecordList(request));
        }

        if (nr.contains(NURS_PERITONEAL_DIALYSIS.getType())) {
            nrRecordList.addAll(recordListDAO.getNrPeritonealDialysisRecordList(request));
        }

        if (nr.contains(NURS_NOTE.getType())) {
            nrRecordList.addAll(recordListDAO.getNrNoteRecordList(request));
        }

        return nrRecordList;
    }

    private List<Record.Response> getCrRecordList(Record.Request request, List<String> cr) {
        List<Record.Response> crRecordList = new ArrayList<>();

        // CPR 발생보고서
        if (cr.contains(CHARACTERIZATION_CPR.getType())) {
            crRecordList.addAll(recordListDAO.getCrCprRecordList(request));
        }

        return crRecordList;
    }

    @Override
    public Chart.Response getChart(HttpSession session, Chart.Request request) {
        ChartContext chartContext = new ChartContext();

        // 기록유형 포멧 불러오기
        List<ChartElement> format = metaRecordService.getRecordFormatList(request.getRecord());

        Object dataSource = null;

        // 서식 내 이미지
        List<String> imageData = new ArrayList<>();

        // 진료기록
        if (request.getRecord().getRecordType().equals(MEDICAL_RECORD.getType())) {
            // 마취 관련
            if (request.getRecord().getRecordDetailType().equals(MEDICAL_ANESTHESIA.getType()) || request.getRecord().getRecordDetailType().equals(MEDICAL_BEFORE_ANESTHESIA.getType()) ) {
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

                if (request.getRecord().getRecordDetailType().equals(MEDICAL_ANESTHESIA.getType())) {
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
                List<MedicalData> medicalData = medicalService.getMedicalData(request.getRecord());

                // 수술기록
                if (request.getRecord().getRecordDetailType().equals(MEDICAL_SURGERY.getType())) {
                    List<SurgeryData> surgeryData = medicalService.getSurgeryData(request.getRecord());
                    List<MedicalData> medicalSurgeryData = MedicalData.of(surgeryData);
                    medicalData.addAll(medicalSurgeryData);
                }

                dataSource = medicalData;
            }

            // 진료기록 내 이미지 정보 확인
            int mdrcId = (int) request.getRecord().getMdrcId();
            int mdrcFomSeq = request.getRecord().getMdrcFomSeq();
            imageData = medicalService.getMedicalImageData(new MedicalImage.Request(mdrcId, mdrcFomSeq));
        }

        // 처방기록
        if (request.getRecord().getRecordDetailType().equals(ORDER_RECORD.getType())) {
            chartContext.setChartStrategy(new OrderChartStrategy());
            dataSource = orderService.getOrderData(session.getAttribute("pev-pid").toString(), request.getRecord());
        }

        // 검사기록
        if (request.getRecord().getRecordType().equals(EXAM_RECORD.getType())) {
            // 병리검사
            if (request.getRecord().getRecordDetailType().equals(EXAM_PATHOLOGY.getType())) {
                chartContext.setChartStrategy(new PathologyChartStrategy());

                PathologyData.Request pathologyDataRequest = new PathologyData.Request();
                pathologyDataRequest.setPthlNo(request.getRecord().getExamKey());

                dataSource = pathologyService.getPathologyData(pathologyDataRequest);
            }

            // 영상검사
            if (request.getRecord().getRecordDetailType().equals(EXAM_PICTURE.getType())) {
                chartContext.setChartStrategy(new PictureChartStrategy());

                PictureData.Request pictureDataRequest = new PictureData.Request();
                pictureDataRequest.setIptnNo(request.getRecord().getExamKey());
                pictureDataRequest.setOrdCd(request.getRecord().getKeyId());

                dataSource = pictureService.getPictureData(pictureDataRequest);
            }

            // 검체검사
            if (request.getRecord().getRecordDetailType().equals(EXAM_SPECIMEN.getType())) {
                chartContext.setChartStrategy(new SpecimenChartStrategy());
                dataSource = specimenService.getSpecimenData(session, request.getRecord());
            }

            // 과별서식 (기능검사는 API 따로 존재)
            if (request.getRecord().getRecordDetailType().equals(MEDICAL_DEPARTMENT.getType())) {
                chartContext.setChartStrategy(new FunctionChartStrategy());
                dataSource = functionService.getFunctionData(request.getRecord().getKeyId());
            }
        }

        // 스캔자료
        if (request.getRecord().getRecordType().equals(SCAN_RECORD.getType())) {
            chartContext.setChartStrategy(new ScanChartStrategy());
            dataSource = request.getRecord();
        }

        // 간호기록
        if (request.getRecord().getRecordType().equals(NURS_RECORD.getType())) {
            // 임상관찰기록
            if (request.getRecord().getRecordDetailType().equals(NURS_OBSERVATION.getType())) {
                chartContext.setChartStrategy(new ObservationChartStrategy());
                dataSource = observationService.getObservationData(session, request.getRecord());
            }

            // 입원간호정보
            if (request.getRecord().getRecordDetailType().equals(NURS_INPATIENT.getType())) {
                chartContext.setChartStrategy(new InpatientChartStrategy());
                format = inpatientService.getInpatientFormat(request.getRecord());
                dataSource = null;
            }

            // 간호활동수행기록
            if (request.getRecord().getRecordDetailType().equals(NURS_EXECUTE.getType())) {
                chartContext.setChartStrategy(new ExecuteChartStrategy());
                format = executeService.getNrExecuteFormat(request.getRecord());
                dataSource = null;
            }

            // 낙상위험도평가
            if (request.getRecord().getRecordDetailType().equals(NURS_FALL.getType())) {
                chartContext.setChartStrategy(new FallChartStrategy());
                dataSource =  fallService.getFallData(request.getRecord().getKeyId());
            }

            // 욕창간호기록
            if (request.getRecord().getRecordDetailType().equals(NURS_BEDSORE.getType())) {
                chartContext.setChartStrategy(new BedsoreChartStrategy());
                format = bedsoreService.getBedsoreFormat(request.getRecord().getKeyId());
                dataSource = null;
            }

            // 욕창위험도평가
            if (request.getRecord().getRecordDetailType().equals(NURS_BEDSORE_EVALUATION.getType())) {
                chartContext.setChartStrategy(new BedsoreEvaluationChartStrategy());
                dataSource = bedsoreService.getBedsoreEvaluationData(request.getRecord().getKeyId());
            }

            // 퇴실간호기록
            if (request.getRecord().getRecordDetailType().equals(NURS_CHECKOUT.getType())) {
                chartContext.setChartStrategy(new CheckoutChartStrategy());
                dataSource = checkoutService.getNrCheckoutData(request.getRecord().getKeyId());
            }

            // 퇴원간호기록
            if (request.getRecord().getRecordDetailType().equals(NURS_DISCHARGE.getType())) {
                chartContext.setChartStrategy(new DischargeChartStrategy());
                dataSource = dischargeService.getNrDischargeData(request.getRecord().getKeyId());
            }

            // 전과전동간호기록
            if (request.getRecord().getRecordDetailType().equals(NURS_TRANSFER.getType())) {
                chartContext.setChartStrategy(new TransferChartStrategy());
                dataSource = transferService.getNrTransferData(request.getRecord().getKeyId());
            }

            // 수술전상태확인
            if (request.getRecord().getRecordDetailType().equals(NURS_STATUS.getType())) {
                chartContext.setChartStrategy(new StatusChartStrategy());
                dataSource = statusService.getNrStatusValueList(request.getRecord().getKeyId());
            }

            // 혈액투석간호기록
            if (request.getRecord().getRecordDetailType().equals(NURS_BLOOD_DIALYSIS.getType())) {
                chartContext.setChartStrategy(new BloodDialysisChartStrategy());
                dataSource = bloodDialysisService.getBloodDialysisData(request.getRecord().getKeyId());
            }

            // 복막투석간호기록
            if (request.getRecord().getRecordDetailType().equals(NURS_PERITONEAL_DIALYSIS.getType())) {
                chartContext.setChartStrategy(new PeritionealDialysisChartStrategy());
                dataSource = peritonealDialysisService.getPeritonealDialysisData(request.getRecord().getKeyId());
            }

            // 간호일지
            if (request.getRecord().getRecordDetailType().equals(NURS_NOTE.getType())) {
                chartContext.setChartStrategy(new NoteChartStrategy());
                dataSource = noteService.getNoteData(request.getRecord().getKeyId());

                // 간호일지 서식 내 이미지
                if (dataSource != null) {
                    NoteData noteData = (NoteData) dataSource;
                    List<String> ndrcIdList = noteData.getValueList().stream().map(NoteValue::getNdrcId).collect(Collectors.toList());
                    if (!ndrcIdList.isEmpty()) {
                        imageData = noteDAO.getImagePath(ndrcIdList);
                    }
                }
            }
        }

        // 특성화 기록
        if (request.getRecord().getRecordType().equals(CHARACTERIZATION_RECORD.getType())) {
            // CPR 발생보고서
            if (request.getRecord().getRecordDetailType().equals(CHARACTERIZATION_CPR.getType())) {
                chartContext.setChartStrategy(new CprChartStrategy());
                dataSource = cprService.getCprData(request.getRecord());
            }
        }

        // 포멧에 Value 값 매칭
        List<ChartElement> data = chartContext.getChartStrategy().getData(format, dataSource);

        // 스타일
        boolean applyStyle = PevChartUtil.applyStyle(request.getRecord().getRecordDetailType());
        List<ChartStyleSection> style = getStyle(request, applyStyle);

        // PID
        String pid = SessionUtil.getPid(session);

        // 각 사용자의 규칙
        boolean withOrigin = getWithOrigin(session);

        ChartData chartData = new ChartData(pid, data, withOrigin);
        if (request.getMaskingYn().equals("Y")) chartData = chartContext.getMaskedData(chartData);

        // 차트 조합 및 정리
        Chart.Response chart = chartContext.getChart(format, chartData.getValues(), style, applyStyle);

        // 차트 나머지 데이터 세팅
        setChartData(chart, request, session, imageData);

        return chart;
    }

    /**
     * 과별서식 테스트 function
     */
    @Override
    public String getDocumentHtml(Chart.Request request) {
        return getDocumentHtml(request, null);
    }

    private String getDocumentHtml(Chart.Request request, HttpSession session) {
        StringJoiner sj = new StringJoiner("\n");

        ChartContext chartContext = new ChartContext();
        chartContext.setChartStrategy(new MedicalChartStrategy());
        Object dataSource = medicalService.getMedicalData(request.getRecord());
        List<ChartElement> format = metaRecordService.getRecordFormatList(request.getRecord());
        List<ChartStyleSection> style = getStyle(request, true);
        List<ChartElement> data = chartContext.getChartStrategy().getData(format, dataSource)
                .stream()
                .filter(c -> StringUtils.isNotEmpty(c.getContent()))
                .collect(Collectors.toList());

        if (request.getRecord().getRecordType().equals(EXAM_RECORD.getType())) {
            Object examDataSource = functionService.getFunctionData(request.getRecord().getKeyId());
            chartContext.setChartStrategy(new FunctionChartStrategy());
            data.addAll(chartContext.getChartStrategy().getData(format, examDataSource)
                    .stream()
                    .filter(c -> StringUtils.isNotEmpty(c.getContent()))
                    .collect(Collectors.toList()));
        }

        if (request.getMaskingYn().equals("Y")) {
            String pid = Objects.nonNull(session) ? SessionUtil.getPid(session) : "MARCO";
            // 각 사용자의 규칙
            boolean withOrigin = Objects.nonNull(session) ? getWithOrigin(session) : true;
            ChartData chartData = new ChartData(pid, data, withOrigin);
            data = chartContext.getMaskedData(chartData).getValues()
                    .stream()
                    .map(d -> {
                        if (d.getControlType().name().equalsIgnoreCase("ImageCheckBox") || d.getControlType().name().equalsIgnoreCase("Image")) {
                            d.setContent(String.format("https://deview.snuh.org/masked_images/%s", imageService.getMaskedImage(ImageDTO.builder()
                                    .refresh(false)
                                    .url(d.getContent())
                                    .build()).getUrl()));
                        }
                        return d;
                    })
                    .collect(Collectors.toList());
        }

        List<ChartDocumentValue> values = data.stream()
                .map(d -> new ChartDocumentValue(d)).collect(Collectors.toList());

        for(ChartStyleSection section : style) {
            sj.add(PevDocumentRenderUtil.render(section.getItems(), values));
        }

        return sj.toString().replace("\n", "").replace("\"", "'");
    }

    /**
     * 스타일
     */
    public List<ChartStyleSection> getStyle(Chart.Request request, boolean applyStyle) {
        List<ChartStyleSection> style = new ArrayList<>();

        if (applyStyle) {
            ChartStyleXml.Request xmlRequest = new ChartStyleXml.Request();
            xmlRequest.setMdfmClsCd(request.getRecord().getRecordDetailType());
            xmlRequest.setMdfmId(request.getRecord().getMdfmId());
            xmlRequest.setMdfmFomSeq(request.getRecord().getMdfmFomSeq());

            style = medicalService.getChartStyleSections(xmlRequest);
        }

        return style;
    }

    /**
     * 차트 나머지 데이터 세팅
     */
    private void setChartData(Chart.Response chart, Chart.Request request, HttpSession session, List<String> imageData) {
        // XML 형식으로 조회하는 기록유형인 경우
        if (XML_RECORD_LIST.contains(request.getRecord().getRecordDetailType())) {
            chart.setHtmlData(getDocumentHtml(request, session));
        }

        // 진료기록 이미지 추가
        if (!imageData.isEmpty()) {
            chart.setMedicalImages(imageData);
        }
    }

    /**
     * 각 사용자의 규칙
     */
    private boolean getWithOrigin(HttpSession session) {
        boolean withOrigin = false;
        String userStr = (String) session.getAttribute("pev-user");

        if (userStr != null) {
            Gson gson = new Gson();
            IdpLoginUser idpLoginUser = gson.fromJson(userStr, IdpLoginUser.class);
            withOrigin = idpLoginUser.getAuthCd().equals("S");
        }

        return withOrigin;
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
