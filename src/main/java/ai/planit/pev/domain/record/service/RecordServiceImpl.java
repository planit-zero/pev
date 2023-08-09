package ai.planit.pev.domain.record.service;

import ai.planit.pev.domain.record.dao.RecordDAO;
import ai.planit.pev.domain.record.dto.CertificateDTO;
import ai.planit.pev.domain.record.dto.DetailRequestDTO;
import ai.planit.pev.domain.record.dto.DetailResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecordServiceImpl implements RecordService {
    private final RecordDAO recordDAO;

    @Override
    public List<CertificateDTO> getCertificateListByPtNo(String ptNo) {
        return recordDAO.getCertificateListByPtNo(ptNo);
    }

    @Override
    public List<DetailResponseDTO> getDetailListByCondition(DetailRequestDTO detailRequestDTO) {
        List<DetailResponseDTO> detailListByCondition = new ArrayList<>();

        detailListByCondition.addAll(recordDAO.getDetailListByCondition(detailRequestDTO));

        return detailListByCondition;
    }
}
