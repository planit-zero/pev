package ai.planit.pev.domain.meta.event.dto;

import ai.planit.pev.domain.ods.record.dto.Record;
import ai.planit.pev.utility.PevStringUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    private int id; // pk
    private EventType type; // 이벤트 타입
    private String ptNo; // 병록번호
    private String searchTargets; // 기록유형
    private String searchFromDate; // 기록일자
    private String searchToDate; // 기록일자
    private String pactTpCd; // 환자구분
    private String deptType; // 진료과
    private String deptCd; // 진료과(작성과)
    private String stfNo; // 사번
    private String stfNm; // 직원명
    private String loadDtm; // 발생일시

    public Event(Record.Request request) {
        this.type = EventType.REPORT;
        this.ptNo = request.getPtNo();
        this.searchTargets = PevStringUtil.getSearchTargetsDesc(request.getSearchTargets());
        this.searchFromDate = request.getSearchFromDate();
        this.searchToDate = request.getSearchToDate();
        this.pactTpCd = request.getPactTpCd();
        this.deptType = request.getDeptType();
        this.deptCd = request.getDeptCd();
    }

}
