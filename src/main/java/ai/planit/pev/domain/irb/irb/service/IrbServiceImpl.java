package ai.planit.pev.domain.irb.irb.service;

import ai.planit.pev.domain.irb.irb.dao.IrbDAO;
import ai.planit.pev.domain.irb.irb.dto.Irb;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IrbServiceImpl implements IrbService {
    private final IrbDAO irbDAO;

    @Override
    public List<Irb> getIrbList(String stfNo) {
        return irbDAO.getIrbList(stfNo);
    }
}
