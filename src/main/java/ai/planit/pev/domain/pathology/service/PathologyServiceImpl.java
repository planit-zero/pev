package ai.planit.pev.domain.pathology.service;

import ai.planit.pev.core.exception.BaseException;
import ai.planit.pev.core.exception.ErrorType;
import ai.planit.pev.domain.pathology.dao.PathologyDAO;
import ai.planit.pev.domain.pathology.dto.PathologyData;
import ai.planit.pev.domain.pathology.dto.PathologyProcess;
import ai.planit.pev.domain.record.dto.Record;
import ai.planit.pev.domain.record.dto.RecordEntity;
import ai.planit.pev.domain.record.dto.RecordSection;
import ai.planit.pev.domain.record.dto.RecordSheet;
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
        recordSheet.setSections(getRecordSections(record));

        return recordSheet;
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
        RecordSection section = new RecordSection();

        List<RecordEntity> entities = new ArrayList<>();
        entities.add(getPathologyEntity(record));
        section.setEntities(entities);

        return section;
    }

    /**
     * 병리검사 엔티티 생성
     *
     * @param record 조회할 기록 정보
     * @return 병리검사 기록 엔티티
     */
    private RecordEntity getPathologyEntity(Record.Response record) {
        PathologyData.Request dataRequest = new PathologyData.Request();
        dataRequest.setPthlNo(record.getExamKey());

        PathologyData.Response pathologyData = pathologyDAO.getPathologyData(dataRequest);

        String acptDt = String.format("접수일 : %s", pathologyData.getAcptDt());
        String lshDt = String.format("판독일 : %s", pathologyData.getLshDt());

        String text = String.format(
                "%s\r\n%s\t%s%s",
                pathologyData.getPlrtLdat(),
                acptDt,
                lshDt,
                getPathologyProcessContent(record)
                );

        return PevEntityUtil.getSimpleTextEntity(true, "", text);
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
                "\r\n제작: %s\t육안: %s\t판독준비: %s\t결과입력: %s",
                createProcess,
                microscopicProcess,
                decodeProcess,
                inputProcess
        );
    }
}
