package ai.planit.pev.domain.ods.patient.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class RidByGid {
    @Getter
    @Setter
    public static class Request {
        private String stfNo;
        private String irbNo;
        private final String hspTpCd = "01";
        private final Boolean isIrbApproved = true;
        private final Boolean isDecryptApproved = false;
        private final Boolean isSearch = false;
        private List<EncryptedPatient> data;
    }

    @Getter
    @Setter
    public static class Response {
        private String irbNo;
        private List<EncryptedPatient> data;
    }
}
