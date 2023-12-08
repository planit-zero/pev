package ai.planit.pev.domain.meta.report.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportDetail {
    private int reportId;
    private int valueSeq;
    private String valueId;
    private String valueParentId;
    private String reportText;
    private String processYn;
    private String processText;
    private String processDtm;
}
