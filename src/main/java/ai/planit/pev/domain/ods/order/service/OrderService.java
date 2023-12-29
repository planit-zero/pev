package ai.planit.pev.domain.ods.order.service;

import ai.planit.pev.domain.ods.record.dto.Record;
import ai.planit.pev.strategy.chart.object.order.OrderData;

public interface OrderService {
    OrderData getOrderData(String ptNo, Record.Response record);
}
