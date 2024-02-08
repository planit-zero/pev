package ai.planit.pev.strategy.chart.object.note;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NoteData {
    private List<NoteGroup> groupList;
    private List<NoteValue> valueList;
}
