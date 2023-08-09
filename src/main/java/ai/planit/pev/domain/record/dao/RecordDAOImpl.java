package ai.planit.pev.domain.record.dao;

import ai.planit.pev.domain.record.dto.CertificateDTO;
import ai.planit.pev.domain.record.dto.DetailRequestDTO;
import ai.planit.pev.domain.record.dto.DetailResponseDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RecordDAOImpl implements RecordDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Override
    public List<CertificateDTO> getCertificateListByPtNo(String ptNo) {
        return sqlSessionTemplate.selectList("getCertificateListByPtNo", ptNo);
    }

    @Override
    public List<DetailResponseDTO> getDetailListByCondition(DetailRequestDTO detailRequestDTO) {
        return sqlSessionTemplate.selectList("getDetailListByCondition", detailRequestDTO);
    }
}
