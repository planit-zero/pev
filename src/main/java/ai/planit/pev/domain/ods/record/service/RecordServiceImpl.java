package ai.planit.pev.domain.ods.record.service;

import ai.planit.pev.core.exception.BaseException;
import ai.planit.pev.core.exception.ErrorType;
import ai.planit.pev.core.webclient.PevWebClient;
import ai.planit.pev.domain.ods.form.service.FormService;
import ai.planit.pev.domain.ods.scan.service.ScanService;
import ai.planit.pev.domain.ods.order.service.OrderService;
import ai.planit.pev.domain.ods.pathology.service.PathologyService;
import ai.planit.pev.domain.ods.picture.service.PictureService;
import ai.planit.pev.domain.ods.record.constant.RecordTarget;
import ai.planit.pev.domain.ods.record.dao.RecordListDAO;
import ai.planit.pev.domain.ods.record.dto.Record;
import ai.planit.pev.domain.ods.record.dto.RecordSheet;
import ai.planit.pev.domain.ods.specimen.service.SpecimenService;
import ai.planit.pev.utility.PevStringUtil;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecordServiceImpl implements RecordService {

    private final RecordListDAO recordListDAO;
    private final OrderService orderService;
    private final SpecimenService specimenService;
    private final PictureService pictureService;
    private final PathologyService pathologyService;
    private final FormService formService;
    private final ScanService scanService;

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

    @Override
    public RecordSheet getRecordSheet(HttpSession session, Record.Response record) {
        RecordSheet sheet = new RecordSheet();

        if (record.getRecordType().equals(RecordTarget.MEDICAL_RECORD.getType())) {
            sheet = formService.getRecordSheet(session, record);
        }
        // 처방기록
        if (record.getRecordDetailType().equals(RecordTarget.ORDER_RECORD.getType())) {
            sheet = orderService.getRecordSheet(session, record);
        }

        // 검체검사
        if (record.getRecordDetailType().equals(RecordTarget.EXAM_SPECIMEN.getType())) {
            sheet = specimenService.getRecordSheet(session, record);
        }

        // 영상검사
        if (record.getRecordDetailType().equals(RecordTarget.EXAM_PICTURE.getType())) {
            sheet = pictureService.getRecordSheet(session, record);
        }

        // 병리검사
        if (record.getRecordDetailType().equals(RecordTarget.EXAM_PATHOLOGY.getType())) {
            sheet = pathologyService.getRecordSheet(session, record);
        }

        // 스캔자료
        if (record.getRecordDetailType().equals(RecordTarget.SCAN_RECORD.getType())) {
            sheet = scanService.getRecordSheet(session, record);
        }
//        return sheet;
        return getMaskedSheet(sheet);
    }

    private RecordSheet getMaskedSheet(RecordSheet sheet) {
        String url = "http://172.26.33.23:28092";
        String uri = "/api/emr/ann-record-sheet";
        WebClient webClient = PevWebClient.getWebClient(url, ErrorType.RID_CONNECTION_TIMEOUT);

        return webClient.post()
                .uri(uri)
                .acceptCharset(StandardCharsets.UTF_8)
                .body(BodyInserters.fromValue(sheet))
                .retrieve()
                .onStatus(HttpStatus::is5xxServerError, this::throwRidServerError)
                .onStatus(HttpStatus::is4xxClientError, this::throwRidServerError)
                .bodyToMono(RecordSheet.class)
                .block();
    }

    private Mono<? extends Throwable> throwRidServerError(ClientResponse response) {
        return response.createException()
                .flatMap(error -> {
                    String body = error.getResponseBodyAsString(StandardCharsets.UTF_8);
                    Map<String, String> errorData = new Gson().fromJson(body, HashMap.class);
                    System.out.println(errorData.get("message"));
                    return Mono.error(new BaseException(ErrorType.ANN_PROCESS_FAILED, ErrorType.ANN_PROCESS_FAILED.getMessage(), errorData.get("message")));
                });
    }
}
