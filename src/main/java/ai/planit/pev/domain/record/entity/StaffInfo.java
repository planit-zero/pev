package ai.planit.pev.domain.record.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "ODS", name = "S_CNLRRUSD")
@Getter
@Setter
public class StaffInfo {
    @Id
    private String stfNo;

    private String hspTpCd;
    private String sid;
    private String stfNm;
    private String aadpCd;
    private String aoaWkdpCd;
    private String aoaExrmCd;
    private String useGrpCd;
    private String useGrpDtlCd;
    private String octyTpCd;
    private String lginPwd;
    private java.sql.Timestamp lginPwdLshDtm;
    private String hrPwd;
    private java.sql.Timestamp hrPwdLshDtm;
    private String salPwd;
    private java.sql.Timestamp salPwdLshDtm;
    private java.sql.Date rtrmDt;
    private String stfNknm;
    private String rrn;
    private String stfYn;
    private String lojpTpCd;
    private String emalAddr;
    private String mtelNo;
    private String hsinTelNo;
    private String cmedYn;
    private String imgnDrYn;
    private String usrRoleGrdCd;
    private String exmCtgCd;
    private String pohUgrpId;
    private String pohDutyAsgnPsbYn;
    private String ranbCfmtPsbYn;
    private String opDtmnPsbYn;
    private String fsrStfNo;
    private java.sql.Timestamp fsrDtm;
    private String fsrPrgmNm;
    private String fsrIpAddr;
    private String lshStfNo;
    private java.sql.Timestamp lshDtm;
    private String lshPrgmNm;
    private String lshIpAddr;
    private String dutyUnitOcrsTpCd;
    private String dutyUnitOcdtTpCd;
    private String prtlLginPwd;
    private String expsUseDeptCd;
    private String expsUseWkdpCd;
    private String tmrsTpCd;
    private java.sql.Timestamp etlDtm;
}
