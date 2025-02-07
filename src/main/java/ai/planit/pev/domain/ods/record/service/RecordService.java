package ai.planit.pev.domain.ods.record.service;

import ai.planit.pev.domain.ods.record.dto.Record;
import ai.planit.pev.strategy.chart.object.common.Chart;
import ai.planit.pev.strategy.chart.object.medical.MedicalReply;

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

    Chart.Response getChart(HttpSession session, Chart.Request request);
    String getDocumentHtml(Chart.Request request);

    MedicalReply.Response getChartReply(HttpSession session, MedicalReply.Request request);

    List<Chart.Response> getFunctionChart(HttpSession session, Chart.Request request);
}
