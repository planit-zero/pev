package ai.planit.pev.domain.ods.record.service;

import ai.planit.pev.core.exception.BaseException;
import ai.planit.pev.core.exception.ErrorType;
import ai.planit.pev.domain.meta.record.service.MetaRecordService;
import ai.planit.pev.domain.ods.anesthesia.service.AnesthesiaService;
import ai.planit.pev.domain.ods.medical.service.MedicalService;
import ai.planit.pev.domain.ods.order.service.OrderService;
import ai.planit.pev.domain.ods.pathology.service.PathologyService;
import ai.planit.pev.domain.ods.picture.service.PictureService;
import ai.planit.pev.domain.ods.record.constant.RecordTarget;
import ai.planit.pev.domain.ods.record.dao.RecordListDAO;
import ai.planit.pev.domain.ods.record.dto.Record;
import ai.planit.pev.strategy.chart.*;
import ai.planit.pev.strategy.chart.object.common.*;
import ai.planit.pev.strategy.chart.object.medical.MedicalReply;
import ai.planit.pev.strategy.chart.object.pathology.PathologyData;
import ai.planit.pev.strategy.chart.object.picture.PictureData;
import ai.planit.pev.utility.PevChartUtil;
import ai.planit.pev.utility.PevStringUtil;
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

        if (medicalRecordTargets.size() > 0) {
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

        if (examRecordTargets.size() > 0) {
            recordList.addAll(getExamRecordList(request, examRecordTargets));
        }

        // TODO: 간호기록 목록 연동

        if (searchTargetList.contains(RecordTarget.SCAN_RECORD.getType())) {
            recordList.addAll(recordListDAO.getScanRecordList(request));
        }

        // TODO: 특성화기록 목록 연동

        // 조건에 따라 여러 기록을 조회하기 때문에 모든 조회가 끝난 후 한번에 정렬한다.
        recordList = recordList
                .stream()
                .sorted(Comparator.comparing(Record.Response::getWritingDate).reversed())
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

        if (generalTypeList.size() > 0) {
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

    public Chart.Response getChart(HttpSession session, Chart.Request request) {
        ChartContext chartContext = new ChartContext();

        Object dataSource = null;

        // 진료기록
        if (request.getRecord().getRecordType().equals(RecordTarget.MEDICAL_RECORD.getType())) {
            if (request.getRecord().getRecordDetailType().equals(RecordTarget.MEDICAL_ANESTHESIA.getType())) {
                chartContext.setChartStrategy(new AnesthesiaRecordChartStrategy());
                dataSource = anesthesiaService.getAnesthesiaRecordData(request.getRecord().getOpExptRegId());
            } else {
                chartContext.setChartStrategy(new MedicalChartStrategy());
                dataSource = medicalService.getMedicalData(request.getRecord());
            }
        }

        if (request.getRecord().getRecordDetailType().equals(RecordTarget.ORDER_RECORD.getType())) {
            chartContext.setChartStrategy(new OrderChartStrategy());
            dataSource = orderService.getOrderData(session.getAttribute("pev-pid").toString(), request.getRecord());
        }

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

        // 스캔자료
        if (request.getRecord().getRecordType().equals(RecordTarget.SCAN_RECORD.getType())) {
            chartContext.setChartStrategy(new ScanChartStrategy());
            dataSource = request.getRecord();
        }

        List<ChartElement> format = metaRecordService.getRecordFormatList(request.getRecord());
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

        ChartData chartData = new ChartData(data);
        if (request.getMaskingYn().equals("Y")) chartData = chartContext.getMaskedData(chartData);

        return chartContext.getChart(format, chartData.getValues(), style, applyStyle);
    }

    @Override
    public MedicalReply.Response getChartReply(HttpSession session, MedicalReply.Request request) {
        Record.Response record = medicalService.getMedicalReplyRecord(request);

        Chart.Request chartRequest = new Chart.Request();

        chartRequest.setMaskingYn(request.getMaskingYn());
        chartRequest.setRecord(record);

        MedicalReply.Response medicalReply = new MedicalReply.Response();

        medicalReply.setChart(getChart(session, chartRequest));
        medicalReply.setRecord(record);

        return medicalReply;
    }
}
