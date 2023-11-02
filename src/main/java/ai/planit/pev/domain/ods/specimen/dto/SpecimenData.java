package ai.planit.pev.domain.ods.specimen.dto;

import lombok.Getter;
import lombok.Setter;

public class SpecimenData {
    @Getter
    @Setter
    public static class Request {
        private String ptNo; // 환자병록번호
        private String spcmNo; // 검체번호
        private String medExmCtgCd; // 검사분류코드
    }

    @Getter
    @Setter
    public static class Response {
        private String eitmAbbr; // 검사명
        private String exrsFsrcDcstLdat; // 검사결과
        private String refCnte; // 참고치
    }
}
