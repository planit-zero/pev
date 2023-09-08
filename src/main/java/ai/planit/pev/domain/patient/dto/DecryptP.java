package ai.planit.pev.domain.patient.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DecryptP {
    private String gid;
    private String irbNo;
    private String stfNo;
    private String stfNm;
    private String deptCd;
    private String deptNm;
}
