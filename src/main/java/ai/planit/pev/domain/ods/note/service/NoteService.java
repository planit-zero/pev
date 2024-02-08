package ai.planit.pev.domain.ods.note.service;

import ai.planit.pev.strategy.chart.object.note.NoteData;

public interface NoteService {
    NoteData getNoteData(String keyId);
}
