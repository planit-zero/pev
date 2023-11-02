package ai.planit.pev.domain.ods.pathology.service;

import ai.planit.pev.core.exception.BaseException;
import ai.planit.pev.core.exception.ErrorType;
import ai.planit.pev.domain.ods.record.constant.RecordElementAlignment;
import ai.planit.pev.domain.ods.record.dto.*;
import ai.planit.pev.domain.ods.pathology.dao.PathologyDAO;
import ai.planit.pev.domain.ods.pathology.dto.PathologyData;
import ai.planit.pev.domain.ods.pathology.dto.PathologyProcess;
import ai.planit.pev.domain.record.dto.*;
import ai.planit.pev.utility.PevEntityUtil;
import ai.planit.pev.utility.PevStringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PathologyServiceImpl implements PathologyService {
    private final PathologyDAO pathologyDAO;

    /** {@inheritDoc} */
    @Override
    public RecordSheet getRecordSheet(HttpSession session, Record.Response record) {
        String pid = (String) session.getAttribute("pev-pid");

        // 세션에 저장된 환자병록번호가 없을 경우 예외 처리한다.
        if (PevStringUtil.isStringEmpty(pid)) {
            throw new BaseException(ErrorType.PID_NOT_FOUND_IN_SESSION);
        }

        RecordSheet recordSheet = new RecordSheet();
        recordSheet.setHeaderSection(getRecordHeaderSection(record));
        recordSheet.setSections(getRecordSections(record));

        return recordSheet;
    }

    /**
     * 병리검사 기록 헤더 섹션 생성
     *
     * @param record 조회할 기록 정보
     * @return 병리검사 기록 헤더 섹션
     */
    private RecordSection getRecordHeaderSection(Record.Response record) {
        RecordSection section = new RecordSection();

        List<RecordEntity> entities = new ArrayList<>();

        RecordEntity entity = new RecordEntity();
        entity.setText(String.format("%s (%s) [%s]", "병리진단", record.getExamKey(), "판독완료"));

        entities.add(entity);
        section.setEntities(entities);

        return section;
    }

    /**
     * 병리검사 기록의 섹션 목록 생성
     *
     * @param record 조회할 기록 정보
     * @return 병리검사 섹션 목록
     */
    private List<RecordSection> getRecordSections(Record.Response record) {
        List<RecordSection> sections = new ArrayList<>();

        // 병리검사는 단일 섹션으로 구성
        sections.add(getRecordSection(record));

        return sections;
    }

    /**
     * 병리검사 섹션 생성
     *
     * @param record 조회할 기록 정보
     * @return 병리검사 기록 섹션
     */
    private RecordSection getRecordSection(Record.Response record) {
        PathologyData.Request dataRequest = new PathologyData.Request();
        dataRequest.setPthlNo(record.getExamKey());

        PathologyData.Response pathologyData = pathologyDAO.getPathologyData(dataRequest);

        RecordSection section = new RecordSection();

        List<RecordEntity> entities = new ArrayList<>();

        entities.add(PevEntityUtil.getSimpleTextEntity(null, "", pathologyData.getPlrtLdat()));
        entities.add(getPathologyProcessEntity(record, pathologyData));

        RecordElement element = new RecordElement();
        element.setAlignment(RecordElementAlignment.RIGHT.getValue());

        RecordEntity writerEntity = PevEntityUtil.getSimpleTextEntity(element, "", pathologyData.getLshStfNm());
        entities.add(writerEntity);

        section.setEntities(entities);

        return section;
    }

    /**
     * 병리검사 프로세스 엔티티 생성
     *
     * @param record 조회할 기록 정보
     * @param pathologyData 병리검사 데이터
     * @return 병리검사 프로세스 엔티티
     */
    private RecordEntity getPathologyProcessEntity(Record.Response record, PathologyData.Response pathologyData) {
        String acptDt = String.format("접수일 : %s", pathologyData.getAcptDt());
        String lshDt = String.format("판독일 : %s", pathologyData.getLshDt());

        String text = String.format(
                "%s\u2003%s\r\n%s",
                acptDt,
                lshDt,
                getPathologyProcessContent(record)
                );

        return PevEntityUtil.getSimpleTextEntity(null, "", text);
    }

    /**
     * 병리 판독 프로세스별 작업자 출력 문자열 생성
     *
     * @param record 조회할 기록의 정보
     * @return 병리 판독 프로세스별 작업자 출력 문자열
     */
    private String getPathologyProcessContent(Record.Response record) {
        PathologyProcess.Request processRequest = new PathologyProcess.Request();
        processRequest.setPthlNo(record.getExamKey());

        List<PathologyProcess.Response> pathologyProcessList = pathologyDAO.getPathologyProcessList(processRequest);

        // 제작
        String createProcess = pathologyProcessList
                .stream()
                .filter(process -> process.getPthlProTpCd().equals("B") || process.getPthlProTpCd().equals("H") || process.getPthlProTpCd().equals("L"))
                .filter(process -> process.getWkFmtStfNm() != null && !process.getWkFmtStfNm().isEmpty())
                .map(PathologyProcess.Response::getWkFmtStfNm)
                .collect(Collectors.joining("/"));

        // 육안
        String microscopicProcess = pathologyProcessList
                .stream()
                .filter(process -> process.getPthlProTpCd().equals("F") || process.getPthlProTpCd().equals("G"))
                .filter(process -> process.getWkFmtStfNm() != null && !process.getWkFmtStfNm().isEmpty())
                .map(PathologyProcess.Response::getWkFmtStfNm)
                .collect(Collectors.joining("/"));

        // 판독준비
        String decodeProcess = pathologyProcessList
                .stream()
                .filter(process -> process.getPthlProTpCd().equals("M"))
                .filter(process -> process.getWkFmtStfNm() != null && !process.getWkFmtStfNm().isEmpty())
                .map(PathologyProcess.Response::getWkFmtStfNm)
                .collect(Collectors.joining("/"));

        // 결과입력
        String inputProcess = pathologyProcessList
                .stream()
                .filter(process -> process.getPthlProTpCd().equals("P"))
                .filter(process -> process.getWkFmtStfNm() != null && !process.getWkFmtStfNm().isEmpty())
                .map(PathologyProcess.Response::getWkFmtStfNm)
                .collect(Collectors.joining("/"));

        return String.format(
                "제작: %s\u2003육안: %s\u2003판독준비: %s\u2003결과입력: %s",
                createProcess,
                microscopicProcess,
                decodeProcess,
                inputProcess
        );
    }
}
