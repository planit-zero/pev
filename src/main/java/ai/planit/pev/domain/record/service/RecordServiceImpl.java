package ai.planit.pev.domain.record.service;

import ai.planit.pev.core.exception.BaseException;
import ai.planit.pev.core.exception.ErrorType;
import ai.planit.pev.domain.record.dao.RecordListDAO;
import ai.planit.pev.domain.record.dto.Record;
import ai.planit.pev.utility.PevStringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecordServiceImpl implements RecordService {

    private final RecordListDAO recordListDAO;

    /** {@inheritDoc} */
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
        if (searchTargetList.contains("OR")) {
            recordList.addAll(recordListDAO.getOrderRecordList(request));
        }

        // 검사
        List<String> examRecordTargets = searchTargetList
                .stream()
                .filter(target -> target.startsWith("EX_"))
                .collect(Collectors.toList());

        if (examRecordTargets.size() > 0) {
            recordList.addAll(getExamRecordList(request, examRecordTargets));
        }

        // TODO: 간호기록 목록 연동

        // TODO: 스캔자료 목록 연동

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
     * @param request 조회할 기록 목록의 상세 조건
     * @param medicalRecordTargets 조회할 진료기록의 상세 정보
     * @return 데이터베이스에서 조회한 진료기록의 상세 목록
     */
    private List<Record.Response> getMedicalRecordList(Record.Request request, List<String> medicalRecordTargets) {
        List<Record.Response> medicalRecordList = new ArrayList<>();

        // 진료기록 - 수술기록
        if (medicalRecordTargets.contains("D005")) {
            String[] queryTargets = {"D005"};
            request.setQueryTargets(queryTargets);

            medicalRecordList.addAll(recordListDAO.getSurgeryRecordList(request));
        }

        // 진료기록 - 퇴원기록
        if (medicalRecordTargets.contains("D006")) {
            String[] queryTargets = {"D006"};
            request.setQueryTargets(queryTargets);

            medicalRecordList.addAll(recordListDAO.getDischargeRecordList(request));
        }

        // 진료기록 - 타과의뢰
        if (medicalRecordTargets.contains("D007")) {
            String[] queryTargets = {"D007"};
            request.setQueryTargets(queryTargets);

            medicalRecordList.addAll(recordListDAO.getRequestRecordList(request));
        }

        // 진료기록 - 마취기록, 마취 전 평가
        if (medicalRecordTargets.contains("D010") || medicalRecordTargets.contains("D011")) {
            String[] queryTargets = {"D010", "D011"};

            if (!medicalRecordTargets.contains("D010")) {
                queryTargets = Arrays.stream(queryTargets)
                        .filter(target -> !target.equals("D010"))
                        .toArray(String[]::new);
            }

            if (!medicalRecordTargets.contains("D011")) {
                queryTargets = Arrays.stream(queryTargets)
                        .filter(target -> !target.equals("D011"))
                        .toArray(String[]::new);
            }

            request.setQueryTargets(queryTargets);

            medicalRecordList.addAll(recordListDAO.getAnesthesiaRecordList(request));
        }

        // 진료기록 - 일반
        List<String> generalTypeList = medicalRecordTargets.stream()
                .filter(type -> !type.equals("D005"))
                .filter(type -> !type.equals("D006"))
                .filter(type -> !type.equals("D007"))
                .filter(type -> !type.equals("D010"))
                .filter(type -> !type.equals("D011"))
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
     * @param request 조회할 기록 목록의 상세 조건
     * @param examRecordTargets 조회할 검사기록의 상세 정보
     * @return 데이터베이스에서 조회한 검사기록의 상세 목록
     */
    private List<Record.Response> getExamRecordList(Record.Request request, List<String> examRecordTargets) {
        List<Record.Response> examRecordList = new ArrayList<>();

        // 영상검사
        if (examRecordTargets.contains("EX_PICTURE")) {
            examRecordList.addAll(recordListDAO.getExamPictureRecordList(request));
        }

        // 병리검사
        if (examRecordTargets.contains("EX_PATHOLOGY")) {

        }

        // 검체검사
        if (examRecordTargets.contains("EX_SPECIMEN")) {

        }

        // 기능검사
        if (examRecordTargets.contains("EX_FUNCTION")) {

        }

        return examRecordList;
    }
}
