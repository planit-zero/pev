package ai.planit.pev.domain.ods.patient.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

public class IdentifiedPatient {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Request {
        private List<String> ridList;
        private String irb;
    }

    @Getter
    @Setter
    public static class Response {
        private List<String> ptNo;
    }
}
