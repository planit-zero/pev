package ai.planit.pev.domain.meta.event.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventSearchTargetDistribution {
    private String searchTargets;
    private int count;
}
