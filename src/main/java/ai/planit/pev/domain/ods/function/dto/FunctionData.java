package ai.planit.pev.domain.ods.function.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FunctionData {
    private String id;
    private String parentId;
    private String controlType;
    private String classType;
    private String text;
    private String textDesc;
}
