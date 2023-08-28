package ai.planit.pev.domain.record.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetailResponseDTO {
    private String recordType;
    private String recordDetailType;
    private String itemType;
    private String itemNm;
    private String writingDate;
    private String writingDeptCd;
    private String writingDeptNm;
    private String writerStfNo;
    private String writerNm;
    private String keyId;
    private String pactId;
    private String pactTpCd;
    private int sortSeq;
    private String note;
    private int mdfmId;
    private int mdfmFomSeq;
    private int mdrcId;
    private int mdrcFomSeq;
    private String examKey;
    private String pacsImgIptnCd;
    private String accsId;
    private String recType;
    private String geneExmYn;
    private String mdrcWrtStsCdYn;
    private String opExptRegId;
}
