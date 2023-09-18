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

    public List<Record.Response> getRecordList(HttpSession session, Record.Request request) {
        String pid = (String) session.getAttribute("pev-pid");

        if (PevStringUtil.isStringEmpty(pid)) {
            throw new BaseException(ErrorType.PID_NOT_FOUND_IN_SESSION);
        }

        request.setPtNo(pid);

        return getRecordListByTargets(request);
    }

    private List<Record.Response> getRecordListByTargets(Record.Request request) {
        List<Record.Response> recordList = new ArrayList<>();

        List<String> searchTargetList = Arrays.asList(request.getSearchTargets());

        // 진료
        List<String> medicalRecordTargets = searchTargetList
                .stream()
                .filter(target -> target.startsWith("D0"))
                .collect(Collectors.toList());

        if (medicalRecordTargets.size() > 0) {
            recordList.addAll(getMedicalRecordList(request, searchTargetList));
        }

        // 처방
//        if (searchTargetList.contains("OR")) {
//            recordList.addAll(recordListDAO.getOrderRecordList(request));
//        }

        // 검사

        // 간호

        // 스캔

        // 특성화

        recordList = recordList
                .stream()
                .sorted(Comparator.comparing(Record.Response::getWritingDate).reversed())
                .collect(Collectors.toList());

        return recordList;
    }

    private List<Record.Response> getMedicalRecordList(Record.Request request, List<String> searchTargetList) {
        List<Record.Response> medicalRecordList = new ArrayList<>();

        if (searchTargetList.contains("D005")) {
            String[] queryTargets = {"D005"};
            request.setQueryTargets(queryTargets);

//            medicalRecordList.addAll(recordListDAO.getSurgeryRecordList(request));
        }

        if (searchTargetList.contains("D006")) {
            String[] queryTargets = {"D006"};
            request.setQueryTargets(queryTargets);

//            medicalRecordList.addAll(recordListDAO.getDischargeRecordList(request));
        }

        if (searchTargetList.contains("D007")) {
            String[] queryTargets = {"D007"};
            request.setQueryTargets(queryTargets);

//            medicalRecordList.addAll(recordListDAO.getDepartmentRecordList(request));
        }

        if (searchTargetList.contains("D010") || searchTargetList.contains("D011")) {
            String[] queryTargets = {"D010", "D011"};

            if (!searchTargetList.contains("D010")) {
                queryTargets = Arrays.stream(queryTargets)
                        .filter(target -> !target.equals("D010"))
                        .toArray(String[]::new);
            }

            if (!searchTargetList.contains("D011")) {
                queryTargets = Arrays.stream(queryTargets)
                        .filter(target -> !target.equals("D011"))
                        .toArray(String[]::new);
            }

            request.setQueryTargets(queryTargets);

//            medicalRecordList.addAll(recordListDAO.getAnesthesiaRecordList(request));
        }

        List<String> generalTypeList = searchTargetList.stream()
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
}
