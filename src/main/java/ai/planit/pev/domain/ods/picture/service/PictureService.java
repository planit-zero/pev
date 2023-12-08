package ai.planit.pev.domain.ods.picture.service;

import ai.planit.pev.strategy.chart.object.picture.PictureData;

public interface PictureService {
    PictureData.Response getPictureData(PictureData.Request request);
}
