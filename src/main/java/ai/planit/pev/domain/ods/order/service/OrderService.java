package ai.planit.pev.domain.ods.order.service;

import ai.planit.pev.domain.ods.record.dto.Record;
import ai.planit.pev.domain.ods.record.dto.RecordSheet;

import javax.servlet.http.HttpSession;

public interface OrderService {
    /**
     * 처방 기록 시트 조회
     *
     * @param session 환자병록번호가 저장된 세션 정보
     * @param record 시트 조회에 필요한 기록 정보
     * @return 처방 기록 시트 정보
     */
    RecordSheet getRecordSheet(HttpSession session, Record.Response record);
}
