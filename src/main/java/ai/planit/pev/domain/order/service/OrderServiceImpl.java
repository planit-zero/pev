package ai.planit.pev.domain.order.service;

import ai.planit.pev.core.exception.BaseException;
import ai.planit.pev.core.exception.ErrorType;
import ai.planit.pev.domain.order.dao.OrderDAO;
import ai.planit.pev.domain.order.dto.OrderData;
import ai.planit.pev.domain.order.dto.OrderSection;
import ai.planit.pev.domain.record.dto.*;
import ai.planit.pev.utility.PevStringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderDAO orderDAO;

    @Override
    public RecordSheet getRecordSheet(HttpSession session, Record.Response record) {
        String pid = (String) session.getAttribute("pev-pid");

        // 세션에 저장된 환자병록번호가 없을 경우 예외 처리한다.
        if (PevStringUtil.isStringEmpty(pid)) {
            throw new BaseException(ErrorType.PID_NOT_FOUND_IN_SESSION);
        }

        RecordSheet recordSheet = new RecordSheet();
        recordSheet.setSections(getRecordSections(pid, record));

        return recordSheet;
    }

    private List<RecordSection> getRecordSections(String pid, Record.Response record) {
        List<RecordSection> recordSections = new ArrayList<>();

        OrderSection.Request sectionReq = new OrderSection.Request();
        sectionReq.setPtNo(pid);
        sectionReq.setMedPactTpCd(record.getPactTpCd());
        sectionReq.setOrdDt(record.getWritingDate());

        List<OrderSection.Response> orderSections = orderDAO.getOrderSectionList(sectionReq);

        for (OrderSection.Response orderSection : orderSections) {
            recordSections.add(getRecordSection(pid, record, orderSection));
        }

        return recordSections;
    }

    private RecordSection getRecordSection(String pid, Record.Response record, OrderSection.Response orderSection) {
        RecordSection recordSection = new RecordSection();

        List<RecordEntity> entities = new ArrayList<>();

        // 각 처방기록별 내용 출력
        entities.add(getOrderContentEntity(pid, record, orderSection));

        // 각 처방기록별 작성자 출력
        entities.add(getOrderWriterEntity(orderSection));

        recordSection.setEntities(entities);
        return recordSection;
    }

    private RecordEntity getOrderContentEntity(String pid, Record.Response record, OrderSection.Response orderSection) {
        RecordEntity entity = new RecordEntity();

        entity.setText(orderSection.getOdaplPopNm());

        OrderData.Request request = new OrderData.Request();
        request.setPtNo(pid);
        request.setMedPactTpCd(record.getPactTpCd());
        request.setOrdDt(record.getWritingDate());
        request.setOdaplPopCd(orderSection.getOdaplPopCd());
        request.setFsrStfNo(orderSection.getFsrStfNo());

        entity.setValues(getOrderContentValues(request));

        return entity;
    }

    private List<RecordValue> getOrderContentValues(OrderData.Request request) {
        List<RecordValue> values = new ArrayList<>();

        List<OrderData.Response> orderDataList = orderDAO.getOrderDataList(request);

        for (OrderData.Response orderData: orderDataList) {
            RecordValue value = new RecordValue();
            // 추후 수행사인, 이력 포함에 대한 조건 연결 필요
            value.setText(orderData.getOrdNm());
            values.add(value);
        }

        return values;
    }

    private RecordEntity getOrderWriterEntity(OrderSection.Response orderSection) {
        RecordEntity entity = new RecordEntity();

        entity.setText("작성자 :");

        List<RecordValue> values = new ArrayList<>();

        RecordValue value = new RecordValue();
        value.setText(orderSection.getFsrStfNm());

        values.add(value);
        entity.setValues(values);

        return entity;
    }
}
