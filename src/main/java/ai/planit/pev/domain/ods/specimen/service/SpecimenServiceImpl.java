package ai.planit.pev.domain.ods.specimen.service;

import ai.planit.pev.core.exception.BaseException;
import ai.planit.pev.core.exception.ErrorType;
import ai.planit.pev.domain.ods.record.dto.Record;
import ai.planit.pev.domain.ods.specimen.dao.SpecimenDAO;
import ai.planit.pev.strategy.chart.object.specimen.SpecimenData;
import ai.planit.pev.strategy.chart.object.specimen.SpecimenRequest;
import ai.planit.pev.utility.PevStringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class SpecimenServiceImpl implements SpecimenService {
    private final SpecimenDAO specimenDAO;

    @Override
    public SpecimenData getSpecimenData(HttpSession session, Record.Response record) {
        String pid = (String) session.getAttribute("pev-pid");

        if (PevStringUtil.isStringEmpty(pid)) {
            throw new BaseException(ErrorType.PID_NOT_FOUND_IN_SESSION);
        }

        SpecimenRequest request = new SpecimenRequest();

        request.setPtNo(pid);
        request.setSpcmNo(record.getExamKey());
        request.setMedExmCtgCd(record.getKeyId());

        SpecimenData specimenData = new SpecimenData();

        specimenData.setSpecimenInfo(specimenDAO.getSpecimenInfo(request));
        specimenData.setSpecimenResults(specimenDAO.getSpecimenResults(request));

        return specimenData;
    }
}
