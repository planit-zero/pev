package ai.planit.pev.domain.ods.transfer.dao;

import ai.planit.pev.strategy.chart.object.transfer.TransferContent;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TransferDAOImpl implements TransferDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Override
    public List<TransferContent> getNrTransferContents(String keyId) {
        return sqlSessionTemplate.selectList("getNrTransferContents", keyId);
    }

    @Override
    public String getNrTransferWriterText(String keyId) {
        return sqlSessionTemplate.selectOne("getNrTransferWriterText", keyId);
    }
}
