package ai.planit.pev.domain.picture.dto;

import lombok.Getter;
import lombok.Setter;

public class PictureData {
    @Getter
    @Setter
    public static class Request {
        private String iptnNo; // 판독번호
    }

    @Getter
    @Setter
    public static class Response {
        private String iptnDtm; // 판독일시
        private String exmDt; // 검사일자
        private String ordNm; // 검사명
        private String iptnCncsCnte; // Conclusion
        private String th1IptnExpl; // Finding
        private String copnCnte; // Clinical Information
        private String th1IpdrStfNm; // 1번째 판독의 직원 번호
        private String th2IpdrStfNm; // 2번째 판독의 직원 번호
        private String th3IpdrStfNm; // 3번째 판독의 직원 번호
        private String th4IpdrStfNm; // 4번째 판독의 직원 번호
        private String th5IpdrStfNm; // 5번째 판독의 직원 번호
        private String th6IpdrStfNm; // 6번째 판독의 직원 번호
    }
}
