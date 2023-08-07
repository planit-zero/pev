package ai.planit.pev.domain.record.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(schema = "ODS", name = "S_MRDDRECM")
@Immutable
@Getter
@Setter
public class RecordBasic implements Serializable {
    @Id
    private int mdrcId;
    @Id
    private int mdrcFomSeq;

    private String hspTpCd;
    private String ptNo;
    private String lstYn;
    private String wrtrDeptCd;
    private Timestamp recDtm;
    private Timestamp mdfRecDtm;
    private String wrtStfNo;
    private String wkStfNo;
    private String wkSid;
    private String medrSid;
    private String mdrcWrtStsCd;
    private String mdrcSbtitCd;
    private String mdrcSbtitInptNm;
    private String mdfmClsCd;
    private String mdfmClsDtlCd;
    private int mdfmId;
    private int mdfmFomSeq;
    private String addChgRsnCnte;
    private String mdrcDcTpCd;
    private Timestamp mdrcDcDtm;
    private String mdrcDcStfNo;
    private String mdrcDcRsnCd;
    private String mdrcDcEtcCnte;
    private String sgkyNo;
    private Date medDt;
    private String mdrcInptMthdCd;
    private String mdrcInptMscrTpCd;
    private String mobSvYn;
    private String pactId;
    private String pactTpCd;
    private String ptMedDeptCd;
    private Timestamp pactDtm;
    private String bobdPtNo;
    private String bindCnte;
    private String bindDtlRsnCnte;
    private Timestamp bindDtm;
    private String bindStfNo;
    private String opExptRegId;
    private String dgnsId;
    private String infcInfCd;
    private String cldgVocId;
    private String prntYn;
    private Timestamp prntDtm;
    private String prntStfNo;
    private String dcfmWrtYn;
    private String wdDeptCd;
    private String prmNo;
    private String infcDclrCmplCd;
    private String dgnsRegYn;
    private String chdrNm;
    private String pfdrNm;
    private String fsrStfNo;
    private Timestamp fsrDtm;
    private String fsrPrgmNm;
    private String fsrIpAddr;
    private String lshStfNo;
    private Timestamp lshDtm;
    private String lshPrgmNm;
    private String lshIpAddr;
    private String infcMgmtRmCfmtYn;
    private String infcMgmtRmCfmtStfNo;
    private Timestamp infcMgmtRmCfmtDtm;
    private Timestamp etlDtm;
}
