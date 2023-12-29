package ai.planit.pev.domain.ods.order.service;

import ai.planit.pev.domain.ods.record.dto.*;
import ai.planit.pev.domain.ods.order.dao.OrderDAO;
import ai.planit.pev.strategy.chart.object.order.OrderData;
import ai.planit.pev.strategy.chart.object.order.OrderSection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderDAO orderDAO;

    @Override
    public OrderData getOrderData(String ptNo, Record.Response record) {
        OrderSection.Request request = new OrderSection.Request();

        request.setPtNo(ptNo);
        request.setMedPactTpCd(record.getPactTpCd());
        request.setOrdDt(record.getWritingDate());
        request.setWritingDeptCd(record.getWritingDeptCd());

        List<OrderSection.Response> sections = orderDAO.getOrderSections(request);

        sections = sections.stream().peek((section) -> section.setContents(orderDAO.getOrderContents(section))).collect(Collectors.toList());

        OrderData orderData = new OrderData();
        orderData.setSections(sections);

        return orderData;
    }
}
