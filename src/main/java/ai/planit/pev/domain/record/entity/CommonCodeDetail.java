package ai.planit.pev.domain.record.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(schema = "ODS", name = "S_CCCCCSTE")
@Immutable
@Getter
@Setter
public class CommonCodeDetail implements Serializable {
    @Id
    private String comnGrpCd;
    @Id
    private String comnCd;

    private String comnCdNm;
    private String comnCdExpl;
    private String scrnMrkSeq;
    private String useYn;
    private String dtrl1Nm;
    private String dtrl2Nm;
    private String dtrl3Nm;
    private String dtrl4Nm;
    private String dtrl5Nm;
    private String dtrl6Nm;
    private String nextgFmrComnCd;
    private String fsrStfNo;
    private java.sql.Timestamp fsrDtm;
    private String fsrPrgmNm;
    private String fsrIpAddr;
    private String lshStfNo;
    private java.sql.Timestamp lshDtm;
    private String lshPrgmNm;
    private String lshIpAddr;
    private java.sql.Timestamp etlDtm;
}
