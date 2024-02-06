package ai.planit.pev.domain.meta.report.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportDetailUpdate {
    private int reportId;
    private int valueSeq;
    private String processText;
}
