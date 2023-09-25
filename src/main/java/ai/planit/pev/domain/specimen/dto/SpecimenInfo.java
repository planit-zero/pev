package ai.planit.pev.domain.specimen.dto;

import lombok.Getter;
import lombok.Setter;

public class SpecimenInfo {
    @Getter
    @Setter
    public static class Request {
        private String ptNo; // 환자병록번호
        private String spcmNo; // 검체번호
        private String medExmCtgCd; // 검사분류ㅗ드
    }

    @Getter
    @Setter
    public static class Response {
        private String exmCtgNm; // 검사명
        private String spcmNm; // 검체명
        private String itemCbVrfcIptnCnte; // 보고자
    }
}
