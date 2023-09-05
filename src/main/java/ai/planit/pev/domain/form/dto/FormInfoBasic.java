package ai.planit.pev.domain.form.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormInfoBasic {
    private int mdrcId;
    private int mdrcFomSeq;
    private int mdfmId;
    private int mdfmFomSeq;
    private String writingDate;
    private String writingDeptNm;
    private String ptMedDeptNm;
    private String writerNm;
}
