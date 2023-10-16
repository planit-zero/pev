package ai.planit.pev.domain.form.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormData {
    private int sectionSeq;
    private String id;
    private String parentId;
    private String controlType;
    private String classType;
    private String text;
    private String textDesc;
}
