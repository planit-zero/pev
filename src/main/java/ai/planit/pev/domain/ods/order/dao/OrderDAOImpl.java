package ai.planit.pev.domain.ods.order.dao;

import ai.planit.pev.domain.ods.order.dto.OrderSection;
import ai.planit.pev.domain.ods.order.dto.OrderData;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderDAOImpl implements OrderDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    /** {@inheritDoc} */
    @Override
    public List<OrderSection.Response> getOrderSectionList(OrderSection.Request request) {
        return sqlSessionTemplate.selectList("getOrderSectionList", request);
    }

    /** {@inheritDoc} */
    @Override
    public List<OrderData.Response> getOrderDataList(OrderData.Request request) {
        return sqlSessionTemplate.selectList("getOrderDataList", request);
    }
}
