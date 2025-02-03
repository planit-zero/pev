package ai.planit.pev.domain.ods.record.dto;

import lombok.*;

public class Record {

    @Getter
    @Setter
    public static class Request {
        private String ptNo; // 병록번호
        private String[] searchTargets; // 기록유형
        private String[] queryTargets;
        private String searchFromDate; // 기록일자
        private String searchToDate; // 기록일자
        private String pactTpCd; // 환자구분
        private String deptType; // 진료과
        private String deptCd; // 진료과 - 작성과
        private String writerType;
        private String writerStfNo;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
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
        private String ptMedDeptCd;
        private String ptMedDeptNm;
        private int sortSeq;
        private String note;
        private int mdfmId;
        private int mdfmFomSeq;
        private long mdrcId;
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
