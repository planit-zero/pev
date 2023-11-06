package ai.planit.pev.domain.irb.irb.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Irb {
    private String irbNo;
    private String irbKrNm;
    private String fromDt;
    private String toDt;
    private String irbTypeDesc;
    private int ptCnt;
}
