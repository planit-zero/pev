package ai.planit.pev.domain.order.dao;

import ai.planit.pev.domain.order.dto.OrderData;
import ai.planit.pev.domain.order.dto.OrderSection;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderDAOImpl implements OrderDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Override
    public List<OrderSection.Response> getOrderSectionList(OrderSection.Request request) {
        return sqlSessionTemplate.selectList("getOrderSectionList", request);
    }

    @Override
    public List<OrderData.Response> getOrderDataList(OrderData.Request request) {
        return sqlSessionTemplate.selectList("getOrderDataList", request);
    }
}
