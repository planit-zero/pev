package ai.planit.pev.domain.record.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CertificateDTO {
    private String recordType;
    private String itemType;
    private String itemNm;
    private String writingDate;
    private String writingDeptCd;
    private String writingDeptNm;
    private String writerStfNo;
    private String writerNm;
    private String keyId;
    private int mdrcId;
    private String mdrcWrtStsCd;
    private String dgnsRerRcdcNo;
    private String prntYn;
    private String pactTpCd;
    private String hspTpCd;
}
