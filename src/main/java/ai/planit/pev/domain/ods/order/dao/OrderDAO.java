package ai.planit.pev.domain.ods.order.dao;

import ai.planit.pev.strategy.chart.object.order.OrderContent;
import ai.planit.pev.strategy.chart.object.order.OrderSection;

import java.util.List;

public interface OrderDAO {
    List<OrderSection.Response> getOrderSections(OrderSection.Request request);

    List<OrderContent> getOrderContents(OrderSection.Response section);
}
