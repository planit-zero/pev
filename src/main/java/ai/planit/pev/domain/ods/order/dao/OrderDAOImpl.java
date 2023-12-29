package ai.planit.pev.domain.ods.order.dao;

import ai.planit.pev.strategy.chart.object.order.OrderContent;
import ai.planit.pev.strategy.chart.object.order.OrderSection;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderDAOImpl implements OrderDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Override
    public List<OrderSection.Response> getOrderSections(OrderSection.Request request) {
        return sqlSessionTemplate.selectList("getOrderSections", request);
    }

    @Override
    public List<OrderContent> getOrderContents(OrderSection.Response section) {
        return sqlSessionTemplate.selectList("getOrderContents", section);
    }
}
