package ai.planit.pev.domain.ods.patient.dto;

import lombok.Getter;
import lombok.Setter;

public class PatientByIrb {

    @Getter
    @Setter
    public static class Request {
        private String irb;
    }

    @Getter
    @Setter
    public static class Response {
        private String id;
        private String name;
        private String dob;
    }
}
