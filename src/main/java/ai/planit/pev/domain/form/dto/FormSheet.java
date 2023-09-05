package ai.planit.pev.domain.form.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FormSheet extends FormInfoBasic {
    private List<FormSection> sections;

    public void setBasicInfo(FormInfoBasic formInfoBasic) {
        this.setMdfmId(formInfoBasic.getMdfmId());
        this.setMdfmFomSeq(formInfoBasic.getMdfmFomSeq());
        this.setMdrcId(formInfoBasic.getMdrcId());
        this.setMdrcFomSeq(formInfoBasic.getMdrcFomSeq());
        this.setItemNm(formInfoBasic.getItemNm());
        this.setWritingDate(formInfoBasic.getWritingDate());
        this.setWritingDateTime(formInfoBasic.getWritingDateTime());
        this.setWritingDeptNm(formInfoBasic.getWritingDeptNm());
        this.setPtMedDeptNm(formInfoBasic.getPtMedDeptNm());
        this.setWriterNm(formInfoBasic.getWriterNm());
    }
}
