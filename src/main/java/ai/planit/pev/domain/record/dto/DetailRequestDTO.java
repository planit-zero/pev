package ai.planit.pev.domain.record.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class DetailRequestDTO {
    private String ptNo;
    private String searchFromDate;
    private String searchToDate;
    private String pactTpCd;
    private String[] recordType;
    private String[] recordDetailType;
    private String deptType;
    private String deptCd;
    private String writerType;
    private String writerStfNo;
    private String detailType;
}
