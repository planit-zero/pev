package ai.planit.pev.domain.ods.note.dao;

import ai.planit.pev.strategy.chart.object.note.NoteGroup;
import ai.planit.pev.strategy.chart.object.note.NoteKey;
import ai.planit.pev.strategy.chart.object.note.NoteValue;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class NoteDAOImpl implements NoteDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Override
    public List<NoteGroup> getNrNoteGroupList(NoteKey noteKey) {
        return sqlSessionTemplate.selectList("getNrNoteGroupList", noteKey);
    }

    @Override
    public List<NoteValue> getNrNoteValueList(NoteKey noteKey) {
        return sqlSessionTemplate.selectList("getNrNoteValueList", noteKey);
    }

    @Override
    public List<String> getImagePath(List<String> ndrcIdList) {
        return sqlSessionTemplate.selectList("getImagePath", ndrcIdList);
    }

}
