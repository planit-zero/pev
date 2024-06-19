package ai.planit.pev.domain.ods.record.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RecordTarget {
    // 진료기록
    MEDICAL_RECORD("DR", "진료기록"),
    MEDICAL_OUTPATIENT_FIRST("D001", "외래초진"),
    MEDICAL_OUTPATIENT_PROGRESS("D002", "외래경과"),
    MEDICAL_INPATIENT_FIRST("D003", "입원초진"),
    MEDICAL_INPATIENT_PROGRESS("D004", "입원경과"),
    MEDICAL_EMERGENCY("D031", "응급기록"),
    MEDICAL_SURGERY("D005", "수술기록"),
    MEDICAL_DISCHARGE("D006", "퇴원기록"),
    MEDICAL_ANESTHESIA("D010", "마취기록"),
    MEDICAL_BEFORE_ANESTHESIA("D011", "마취전평가"),
    MEDICAL_REQUEST("D007", "타과의뢰"),
    MEDICAL_DEPARTMENT("D020", "과별서식"),
    MEDICAL_COVER("D030", "의무기록표지"),
    CERTIFICATE_REQUEST("D009", "진단서/의뢰서"),
    CERTIFICATE_ACCIDENT("D035", "산재진단서"),
    CONSENT_NOTE("D033", "동의서"),

    // 처방
    ORDER_RECORD("OR", "처방기록"),

    // 검사
    EXAM_RECORD("EX", "검사기록"),
    EXAM_PICTURE("EX_PICTURE", "영상검사"),
    EXAM_PATHOLOGY("EX_PATHOLOGY", "병리검사"),
    EXAM_SPECIMEN("EX_SPECIMEN", "검체검사"),
    EXAM_FUNCTION("EX_FUNCTION", "기능검사"),

    // 간호
    NURS_RECORD("NR", "간호기록"),
    NURS_INPATIENT("NR_INPATIENT", "입원간호정보"),
    NURS_OBSERVATION("NR_OBSERVATION", "임상관찰기록"),
    NURS_EXECUTE("NR_EXECUTE", "간호활동수행기록"),
    NURS_FALL("NR_FALL", "낙상위험도평가"),
    NURS_BEDSORE("NR_BEDSORE", "욕창간호기록"),
    NURS_BEDSORE_EVALUATION("NR_BEDSORE_EVALUATION", "욕창위험도평가"),
    NURS_CHECKOUT("NR_CHECKOUT", "퇴실간호기록"),
    NURS_DISCHARGE("NR_DISCHARGE", "퇴원간호기록"),
    NURS_TRANSFER("NR_TRANSFER", "전과전동간호기록"),
    NURS_STATUS("NR_STATUS", "수술전상태확인"),
    NURS_BLOOD_DIALYSIS("NR_BLOOD_DIALYSIS", "혈액투석간호기록"),
    NURS_PERITONEAL_DIALYSIS("NR_PERITONEAL_DIALYSIS", "복막투석간호기록"),
    NURS_NOTE("NR_NOTE", "간호일지"),

    // 스캔자료
    SCAN_RECORD("SC", "스캔자료"),

    // 특성화기록
    CHARACTERIZATION_RECORD("CR", "특성화기록"),
    ;


    private final String type;
    private final String desc;
}
