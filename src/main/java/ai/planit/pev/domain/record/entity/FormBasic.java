package ai.planit.pev.domain.record.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(schema = "ODS", name = "S_MRFMFORM")
@Immutable
@Getter
@Setter
public class FormBasic implements Serializable {
    @Id
    private int mdfmId;
    @Id
    private int mdfmFomSeq;

    private String mdfmNm;
    private String mdfmClsCd;
    private String mdfmClsDtlCd;
    private String mdfmApyCtraCd;
    private String ownIfyTpCd;
    private String ownIfyNo;
    private String bscFormYn;
    private String formUstsCd;
    private String cpUseYn;
    private String dsrcAknlYn;
    private String dgsgTgtYn;
    private String lstYn;
    private String mdfmSctnClsCd;
    private String mdfmExplCnte;
    private String mtdFormSmryEmplYn;
    private String scrnSortSeq;
    private String fsrStfNo;
    private Timestamp fsrDtm;
    private String fsrPrgmNm;
    private String fsrIpAddr;
    private String lshStfNo;
    private Timestamp lshDtm;
    private String lshPrgmNm;
    private String lshIpAddr;
    private Timestamp etlDtm;
}
