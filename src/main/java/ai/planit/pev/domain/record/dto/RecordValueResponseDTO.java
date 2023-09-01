package ai.planit.pev.domain.record.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecordValueResponseDTO {
    private int mdrcId;
    private int mdrcFomSeq;
    private String mdfmCpemId;
    private String mdfmCpemNo;
    private String dmrcCmpsSeq;
    private String recValSeq;
    private String mdfmElmtCclsCd;
    private String mdfmElmtOptpCd;
    private String sgkyNo;
    private String mgptTpChrVal;
    private String valueType;
    private String mdfmElmtInptCnte;
    private String dcstLdat;
    private String fsrcDcstLdat;
    private String fchgDcstLdat;
    private String imgTmplPathNm;
    private String imgNoatCnte;
    private String intgImgFiilePathNm;
}
