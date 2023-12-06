package ai.planit.pev.domain.ods.pathology.dao;

import ai.planit.pev.strategy.chart.object.pathology.PathologyContent;
import ai.planit.pev.strategy.chart.object.pathology.PathologyData;
import ai.planit.pev.strategy.chart.object.pathology.PathologyProcess;

import java.util.List;

public interface PathologyDAO {
    /**
     * 병리검사 기록지 데이터 조회
     *
     * @param request 병리번호
     * @return 병리검사 기록지 데이터
     */
    PathologyContent getPathologyData(PathologyData.Request request);

    /**
     * 병리검사 기록지 작업 정보 조회
     *
     * @param request 병리번호
     * @return 병리검사 기록지 작업 정보 목록
     */
    List<PathologyProcess> getPathologyProcessList(PathologyData.Request request);
}
