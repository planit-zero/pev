package ai.planit.pev.domain.ods.note.service;

import ai.planit.pev.domain.ods.note.dao.NoteDAO;
import ai.planit.pev.strategy.chart.object.note.NoteData;
import ai.planit.pev.strategy.chart.object.note.NoteKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {
    private final NoteDAO noteDAO;

    @Override
    public NoteData getNoteData(String keyId) {
        String[] keyIdArr = keyId.split("_");

        NoteKey noteKey = new NoteKey();

        noteKey.setPtNo(keyIdArr[0]);
        noteKey.setInptDtm(keyIdArr[1]);

        NoteData noteData = new NoteData();

        noteData.setGroupList(noteDAO.getNrNoteGroupList(noteKey));
        noteData.setValueList(noteDAO.getNrNoteValueList(noteKey));

        return noteData;
    }
}
