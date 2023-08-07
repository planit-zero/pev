package ai.planit.pev.domain.record.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class Certificate {
    private String recordType;
    private String itemType;
    private String itemNm;
    private Timestamp writingDate;
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

    @QueryProjection
    public Certificate(String recordType, String itemType, String itemNm, Timestamp writingDate, String writingDeptCd, String writingDeptNm, String writerStfNo, String writerNm, String keyId, int mdrcId, String mdrcWrtStsCd, String dgnsRerRcdcNo, String prntYn, String pactTpCd, String hspTpCd) {
        this.recordType = recordType;
        this.itemType = itemType;
        this.itemNm = itemNm;
        this.writingDate = writingDate;
        this.writingDeptCd = writingDeptCd;
        this.writingDeptNm = writingDeptNm;
        this.writerStfNo = writerStfNo;
        this.writerNm = writerNm;
        this.keyId = keyId;
        this.mdrcId = mdrcId;
        this.mdrcWrtStsCd = mdrcWrtStsCd;
        this.dgnsRerRcdcNo = dgnsRerRcdcNo;
        this.prntYn = prntYn;
        this.pactTpCd = pactTpCd;
        this.hspTpCd = hspTpCd;
    }
}
