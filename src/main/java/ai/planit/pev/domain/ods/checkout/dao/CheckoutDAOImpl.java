package ai.planit.pev.domain.ods.checkout.dao;

import ai.planit.pev.strategy.chart.object.checkout.CheckoutContent;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CheckoutDAOImpl implements CheckoutDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Override
    public List<CheckoutContent> getNrCheckoutContents(String keyId) {
        return sqlSessionTemplate.selectList("getNrCheckoutContents", keyId);
    }

    @Override
    public String getNrCheckoutWriterText(String keyId) {
        return sqlSessionTemplate.selectOne("getNrCheckoutWriterText", keyId);
    }
}
