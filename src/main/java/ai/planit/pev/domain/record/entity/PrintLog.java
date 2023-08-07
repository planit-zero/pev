package ai.planit.pev.domain.record.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(schema = "ODS", name="S_MRDDREPG")
@Immutable
@Getter
@Setter
public class PrintLog implements Serializable {
    @Id
    private String prntStfNo;
    @Id
    private java.sql.Date prntDt;
    @Id
    private String prntSeq;

    private java.sql.Timestamp prntDtm;
    private String prntStfMedDeptCd;
    private String prntStfIpAddr;
    private String prntPtTpCd;
    private String ptNo;
    private String ptHmeDeptCd;
    private java.sql.Timestamp pactDtm;
    private String pactId;
    private String prntTpCd;
    private String prntYn;
    private String prntRecLclsCd;
    private String prntRecSclsCd;
    private String prntDtlSeq;
    private String prntDtlCnte;
    private String prntRsnCd;
    private String rmkCnte;
    private String prntHisPrgmTpCd;
    private String rcdcKndNm;
    private String rcdcSeqNm;
    private int mdrcId;
    private int mdrcFomSeq;
    private java.sql.Date prntPrdStrDt;
    private java.sql.Date prntPrdEndDt;
    private java.sql.Timestamp wrtDtm;
    private java.sql.Timestamp fsrDtm;
    private String fsrStfNo;
    private String fsrPrgmNm;
    private String fsrIpAddr;
    private java.sql.Timestamp lshDtm;
    private String lshStfNo;
    private String lshPrgmNm;
    private String lshIpAddr;
    private java.sql.Timestamp etlDtm;
}
