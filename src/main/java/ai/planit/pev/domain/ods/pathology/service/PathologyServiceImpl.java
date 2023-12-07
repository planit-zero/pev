package ai.planit.pev.domain.ods.pathology.service;

import ai.planit.pev.domain.ods.pathology.dao.PathologyDAO;
import ai.planit.pev.strategy.chart.object.pathology.PathologyData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PathologyServiceImpl implements PathologyService {
    private final PathologyDAO pathologyDAO;

    public PathologyData.Response getPathologyData(PathologyData.Request request) {
        PathologyData.Response pathologyData = new PathologyData.Response();

        pathologyData.setPathologyContent(pathologyDAO.getPathologyData(request));
        pathologyData.setPathologyProcessList(pathologyDAO.getPathologyProcessList(request));

        return pathologyData;
    }
}
