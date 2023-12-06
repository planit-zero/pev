package ai.planit.pev.domain.ods.record.service;

import ai.planit.pev.domain.ods.record.dto.RecordSheet;
import ai.planit.pev.domain.ods.record.dto.Record;
import ai.planit.pev.strategy.chart.object.common.Chart;
import ai.planit.pev.strategy.chart.object.common.ChartElement;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface RecordService {

    /**
     * 기록 목록 가져오기
     *
     * @param session 환자병록번호가 저장된 세션 정보
     * @param request 조회할 기록 목록의 상세 조건
     * @return 데이터베이스에서 조회한 기록 목록
     */
    List<Record.Response> getRecordList(HttpSession session, Record.Request request);

    RecordSheet getRecordSheet(HttpSession session, Record.Response record);

    Chart getChart(Record.Response record);
}
