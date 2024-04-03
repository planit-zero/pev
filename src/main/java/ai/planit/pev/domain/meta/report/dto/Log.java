package ai.planit.pev.domain.meta.report.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Log {
    private int rowNum;
    private String stfNo; // 사번
    private String stfNm; // 직원명
    private String deptCd; // 부서코드
    private String deptNm; // 부서명
    private String authCd;
    private String loginDtm; // 접속일시
}
