package ai.planit.pev.domain.record.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetailRequestDTO {
    private String ptNo;
    private String searchFromDate;
    private String searchToDate;
    private String pactTpCd;
    private String[] recordType;
    private String recordDetailType;
    private String deptType;
    private String deptCd;
    private String writerType;
    private String writerStfNo;
    private String detailType;
}
