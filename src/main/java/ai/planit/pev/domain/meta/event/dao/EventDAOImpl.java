package ai.planit.pev.domain.meta.event.dao;

import ai.planit.pev.domain.meta.event.dto.Event;
import ai.planit.pev.domain.meta.event.dto.EventSearchTargetDistribution;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class EventDAOImpl implements EventDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Override
    public void insertEvent(Event event) {
        sqlSessionTemplate.insert("insertEvent", event);
    }

    @Override
    public List<Event> getLogEvent() {
        return sqlSessionTemplate.selectList("getLogEvent");
    }

    @Override
    public List<EventSearchTargetDistribution> getEventSearchTargetDistribution() {
        return sqlSessionTemplate.selectList("getEventSearchTargetDistribution");
    }
}
