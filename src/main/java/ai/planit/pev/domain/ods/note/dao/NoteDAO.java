package ai.planit.pev.domain.ods.note.dao;

import ai.planit.pev.strategy.chart.object.note.NoteGroup;
import ai.planit.pev.strategy.chart.object.note.NoteKey;
import ai.planit.pev.strategy.chart.object.note.NoteValue;

import java.util.List;

public interface NoteDAO {
    List<NoteGroup> getNrNoteGroupList(NoteKey noteKey);
    List<NoteValue> getNrNoteValueList(NoteKey noteKey);
    List<String> getImagePath(List<String> ndrcIdList);
}
