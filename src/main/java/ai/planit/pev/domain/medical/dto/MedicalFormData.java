package ai.planit.pev.domain.medical.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicalFormData {
    private int sectionSeq;
    private String id;
    private String parentId;
    private String controlType;
    private String classType;
    private String text;
}
