package ai.planit.pev.domain.meta.report.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Report {
    private int rowNum;
    private int reportId;
    private String irb;
    private String rid;
    private String recordInfo;
    private String reportUser;
    private String reportDtm;
    private String processYn;
    private int allCount;
    private int processCount;
}
