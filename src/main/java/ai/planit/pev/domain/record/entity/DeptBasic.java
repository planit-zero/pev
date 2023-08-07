package ai.planit.pev.domain.record.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "ODS", name = "S_PDEDBMSM")
@Immutable
@Getter
@Setter
public class DeptBasic {
    @Id
    private String deptCd;
    private String hspTpCd;
    private String deptNm;
    private String deptEngNm;
    private String uprDeptCd;
    private String deptLvlSeq;
    private String medClsCd;
    private String rpdDeptCd;
    private String hiraMedSbjtCd;
    private String hiraMedSbjtSeq;
    private String hiraMtflCd;
    private String bgchgDeptCd;
    private java.sql.Date chgDt;
    private String hrUseYn;
    private String fnclUseYn;
    private String pcostYn;
    private String otptYn;
    private String wdYn;
    private String mtdYn;
    private String odrerClsCd;
    private String ordPblClsCd;
    private String ordFmtClsCd;
    private String exmRsvClsCd;
    private String arclDmdClsCd;
    private String medSortChrVal;
    private String rlvBlndDeptCd;
    private java.sql.Date useStopDt;
    private String sortSeq;
    private String clctnApcbDeptYn;
    private String mtdCtgCd;
    private String mtdRprnDeptCd;
    private String ordPblDeptCd;
    private String hiraInmedDtlMedSbjtCd;
    private String cncrHspYn;
    private String cncrCtrDeptCd;
    private String csltOtptYn;
    private String stfcDeptEngNm;
    private String flrnoChrVal;
    private String hiraMtdDeptEngCd;
    private String upmsDeptCd;
    private String mtdUprDeptCd;
    private String mdctDeptCd;
    private String imgnDeptYn;
    private String rditRlvDeptYn;
    private String fsrStfNo;
    private java.sql.Timestamp fsrDtm;
    private String fsrPrgmNm;
    private String fsrIpAddr;
    private String lshStfNo;
    private java.sql.Timestamp lshDtm;
    private String lshPrgmNm;
    private String lshIpAddr;
    private String hdwkStfNm;
    private String hdwkPrgmNm;
    private String hdwkIpAddr;
    private String spcCd;
    private String mobRsvpYn;
    private java.sql.Timestamp etlDtm;
}
