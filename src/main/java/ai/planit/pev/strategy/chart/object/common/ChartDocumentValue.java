package ai.planit.pev.strategy.chart.object.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChartDocumentValue {
    private int sectionId;
    private boolean used;
    private String id;
    private String parentId;
    private String mdfmCpemNo;
    private String content;
    public ChartDocumentValue(ChartElement chartElement) {
        this.id = chartElement.getId();
        this.sectionId = chartElement.getSectionId();
        this.parentId = chartElement.getParentId();
        this.mdfmCpemNo = chartElement.getMdfmCpemNo();
        this.content = chartElement.getContent();
    }
}
