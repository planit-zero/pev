package ai.planit.pev.domain.ods.patient.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Patient {
    private String id;
    private String name;
    private String gender;
    private String dob;
}
