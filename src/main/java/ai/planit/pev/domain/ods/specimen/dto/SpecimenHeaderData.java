package ai.planit.pev.domain.ods.specimen.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpecimenHeaderData {
    private String ordCtgNm; // 처방분류명
    private String pbsoDeptCd; // 의뢰처
    private String ptHmeDeptCd; // 진료과
    private String andrStfNm; // 의뢰의사
    private String ordDt; // 의뢰일시
    private String acptDtm; // 접수일시
    private String brfgDtm; // 보고일시
}
