package ai.planit.pev.domain.ods.record.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RecordSheet {
    private RecordSection headerSection;
    private List<RecordSection> sections;
}
