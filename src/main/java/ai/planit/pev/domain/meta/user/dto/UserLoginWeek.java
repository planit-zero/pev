package ai.planit.pev.domain.meta.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginWeek {
    private String weekFromMonday;
    private int count;
}
