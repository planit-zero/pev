package ai.planit.pev.domain.ods.pathology.service;

import ai.planit.pev.domain.ods.record.dto.RecordSheet;
import ai.planit.pev.domain.ods.record.dto.Record;
import ai.planit.pev.strategy.chart.object.pathology.PathologyData;

import javax.servlet.http.HttpSession;

public interface PathologyService {
    /**
     * 병리검사 기록 시트 조회
     *
     * @param session 환자병록번호가 저장된 세션 정보
     * @param record 시트 조회에 필요한 기록 정보
     * @return 검체검사 기록 시트 정보
     */
    RecordSheet getRecordSheet(HttpSession session, Record.Response record);

    PathologyData.Response getPathologyData(PathologyData.Request request);
}
