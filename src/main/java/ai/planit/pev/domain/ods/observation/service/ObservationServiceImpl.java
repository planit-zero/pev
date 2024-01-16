package ai.planit.pev.domain.ods.observation.service;

import ai.planit.pev.core.exception.BaseException;
import ai.planit.pev.core.exception.ErrorType;
import ai.planit.pev.domain.ods.observation.dao.ObservationDAO;
import ai.planit.pev.domain.ods.record.dto.Record;
import ai.planit.pev.strategy.chart.object.observation.ObservationData;
import ai.planit.pev.strategy.chart.object.observation.ObservationRequest;
import ai.planit.pev.utility.PevStringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class ObservationServiceImpl implements ObservationService {
    private final ObservationDAO observationDAO;

    @Override
    public ObservationData getObservationData(HttpSession session, Record.Response record) {
        String pid = (String) session.getAttribute("pev-pid");

        if (PevStringUtil.isStringEmpty(pid)) {
            throw new BaseException(ErrorType.PID_NOT_FOUND_IN_SESSION);
        }

        ObservationRequest request = new ObservationRequest();

        request.setPtNo(pid);
        request.setWritingDate(record.getWritingDate());
        request.setPactTpCd(record.getPactTpCd());

        ObservationData observationData = new ObservationData();
        observationData.setContents(observationDAO.getObservationContents(request));

        return observationData;
    }
}
