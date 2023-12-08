package ai.planit.pev.domain.ods.picture.dao;

import ai.planit.pev.strategy.chart.object.picture.PictureData;

public interface PictureDAO {
    /**
     * 영상검사 기록의 데이터 목록을 조회한다.
     *
     * @param request 판독번호
     * @return 영상검사 기록 데이터 목록
     */
    PictureData.Response getPictureData(PictureData.Request request);
}
