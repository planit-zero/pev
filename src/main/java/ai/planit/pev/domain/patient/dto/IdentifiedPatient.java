package ai.planit.pev.domain.patient.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class IdentifiedPatient {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Request {
        private String gid;
        private String irbNo;
        private String stfNo;
        private String stfNm;
        private String deptCd;
        private String deptNm;
    }

    @Getter
    @Setter
    public static class Response {
        private String ptNo;
    }
}
