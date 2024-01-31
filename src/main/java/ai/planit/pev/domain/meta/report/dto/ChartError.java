package ai.planit.pev.domain.meta.report.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChartError {
    private String pid;
    private String stfNo;
    private String targetRecord;
}
