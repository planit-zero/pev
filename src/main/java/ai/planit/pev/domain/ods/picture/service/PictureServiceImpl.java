package ai.planit.pev.domain.ods.picture.service;

import ai.planit.pev.domain.ods.picture.dao.PictureDAO;
import ai.planit.pev.strategy.chart.object.picture.PictureData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PictureServiceImpl implements PictureService {
    private final PictureDAO pictureDAO;

    @Override
    public PictureData.Response getPictureData(PictureData.Request request) {
        return pictureDAO.getPictureData(request);
    }
}
