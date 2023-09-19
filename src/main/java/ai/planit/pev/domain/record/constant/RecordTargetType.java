package ai.planit.pev.domain.record.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RecordTargetType {
    MEDICAL_RECORD("D0", "진료기록"),
    MEDICAL_SURGERY("D005", "진료기록 - 수술기록"),
    MEDICAL_DISCHARGE("D006", "진료기록 - 퇴원기록"),
    MEDICAL_REQUEST("D007", "진료기록 - 타과의뢰"),
    MEDICAL_ANESTHESIA("D010", "진료기록 - 마취기록"),
    MEDICAL_BEFORE_ANESTHESIA("D011", "진료기록 - 마취전평가"),
    ORDER_RECORD("OR", "처방기록"),

    EXAM_RECORD("EX_", "검사기록"),
    EXAM_PICTURE("EX_PICTURE", "영상검사"),
    EXAM_PATHOLOGY("EX_PATHOLOGY", "병리검사"),
    EXAM_SPECIMEN("EX_SPECIMEN", "검체검사"),
    EXAM_FUNCTION("EX_FUNCTION", "기능검사"),

    NURS_RECORD("NR", "간호기록"),

    SCAN_RECORD("SC", "스캔자료"),


    ;


    private final String code;
    private final String desc;
}
