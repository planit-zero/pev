package ai.planit.pev.domain.meta.report.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChartError {
    private int rowNum;
    private String errId;
    private String stfNo;
    private String irb;
    private String rid;
    private String targetRecord;
    private String loadDtm;
    private String processYn;
    private String processDtm;
}
