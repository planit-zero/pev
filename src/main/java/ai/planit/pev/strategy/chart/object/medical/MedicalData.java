package ai.planit.pev.strategy.chart.object.medical;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicalData {
    private int sectionId;
    private String id;
    private String parentId;
    private String content;
    private String desc;
}
