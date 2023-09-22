package ai.planit.pev.domain.order.dao;

import ai.planit.pev.domain.order.dto.OrderData;
import ai.planit.pev.domain.order.dto.OrderSection;

import java.util.List;

public interface OrderDAO {
    List<OrderSection.Response> getOrderSectionList(OrderSection.Request request);
    List<OrderData.Response> getOrderDataList(OrderData.Request request);
}
