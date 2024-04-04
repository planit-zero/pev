package ai.planit.pev.domain.meta.event.dao;

import ai.planit.pev.domain.meta.event.dto.Event;
import ai.planit.pev.domain.meta.event.dto.EventSearchTargetDistribution;

import java.util.List;

public interface EventDAO {
    void insertEvent(Event event);

    List<Event> getLogEvent();

    List<EventSearchTargetDistribution> getEventSearchTargetDistribution();
}
