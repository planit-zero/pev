package ai.planit.pev.domain.meta.report.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Log {
    private int rowNum;
    private String stfNo;
    private String stfNm;
    private String deptCd;
    private String deptNm;
    private String authCd;
    private String loginDtm;
}
