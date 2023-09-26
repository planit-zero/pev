package ai.planit.pev.domain.pathology.dto;

import lombok.Getter;
import lombok.Setter;

public class PathologyProcess {
    @Getter
    @Setter
    public static class Request {
        private String pthlNo; // 병리번호
    }

    @Getter
    @Setter
    public static class Response {
        private String pthlProTpCd; // 병리처리구분코드
        private String pthlProTpNm; // 병리처리구분명
        private String wkFmtStfNm; // 작업수행직원명
    }
}
