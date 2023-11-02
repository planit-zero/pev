package ai.planit.pev.domain.ods.order.dao;

import ai.planit.pev.domain.ods.order.dto.OrderSection;
import ai.planit.pev.domain.ods.order.dto.OrderData;

import java.util.List;

public interface OrderDAO {
    /**
     * 처방기록의 섹션 정보 조회
     *
     * @param request 환자병록번호, 환자구분코드, 처방일자
     * @return 처방기록 섹션 목록
     */
    List<OrderSection.Response> getOrderSectionList(OrderSection.Request request);

    /**
     * 처방기록의 데이터 조회
     *
     * @param request 환자병록번호, 환자구분코드, 처방일자, 처방적용목적코드, 최초등록직원번호
     * @return 처방기록 데이터 목록
     */
    List<OrderData.Response> getOrderDataList(OrderData.Request request);
}
