package ai.planit.pev.domain.record.dto;

import lombok.Getter;
import lombok.Setter;

public class Record {

    @Getter
    @Setter
    public static class Request {
        private String ptNo;
        private String[] searchTargets;
        private String[] queryTargets;
        private String searchFromDate;
        private String searchToDate;
        private String pactTpCd;
        private String deptType;
        private String deptCd;
        private String writerType;
        private String writerStfNo;
    }

    @Getter
    @Setter
    public static class Response {
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
        private String pactTpNm;
        private String sortSeq;
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
}
