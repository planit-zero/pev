package ai.planit.pev.domain.ods.pathology.dto;

import lombok.Getter;
import lombok.Setter;

public class PathologyData {
    @Getter
    @Setter
    public static class Request {
        private String pthlNo; // 병리번호
    }

    @Getter
    @Setter
    public static class Response {
        private String plrtLdat; // 병리결과자료
        private String acptDt; // 접수일
        private String lshDt; // 판독일 (추후 검증 필요)
        private String lshStfNm; // 판독의 (추후 검증 필요)
    }
}
