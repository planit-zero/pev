package ai.planit.pev.domain.specimen.dto;

import lombok.Getter;
import lombok.Setter;

public class SpecimenInfo {
    @Getter
    @Setter
    public static class Request {
        private String ptNo;
        private String spcmNo;
        private String medExmCtgCd;
    }

    @Getter
    @Setter
    public static class Response {
        private String exmCtgNm;
        private String spcmNm;
        private String itemCbVrfcIptnCnte;
    }
}
