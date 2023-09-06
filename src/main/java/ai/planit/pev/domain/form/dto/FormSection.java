package ai.planit.pev.domain.form.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FormSection {
    private int mdfmId;
    private int mdfmFormSeq;
    private String mdfmClsCd;
    private int mdfmSctnSeq;
    private List<FormEntity> entities;
}
