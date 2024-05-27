package ai.planit.pev.domain.ods.medical.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class MedicalImage {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Request {
        private int mdrcId;
        private int mdrcFomSeq;
    }

}
