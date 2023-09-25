package ai.planit.pev.domain.specimen.dto;

import lombok.Getter;
import lombok.Setter;

public class SpecimenData {
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
        private String eitmAbbr;
        private String exrsFsrcDcstLdat;
        private String refCnte;
    }
}
