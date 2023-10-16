package ai.planit.pev.domain.record.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RecordSection {
    private String width;
    private String height;
    private List<RecordEntity> entities;
}
