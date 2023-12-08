package ai.planit.pev.domain.meta.report.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Report {
    private int reportId;
    private String recordInfo;
    private String reportUser;
    private String reportDtm;
}
