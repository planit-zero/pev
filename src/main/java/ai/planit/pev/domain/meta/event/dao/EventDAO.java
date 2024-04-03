package ai.planit.pev.domain.meta.event.dao;

import ai.planit.pev.domain.meta.event.dto.Event;

public interface EventDAO {
    void insertEvent(Event event);
}
