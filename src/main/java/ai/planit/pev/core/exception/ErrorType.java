package ai.planit.pev.core.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorType {
    // Patient
    RID_NOT_FOUND(400, "연구별 환자 ID가 입력되지 않았습니다."),
    PID_NOT_FOUND(404, "입력한 가명화 환자 ID에 해당하는 환자병록번호가 존재하지 않습니다."),
    PATIENT_NOT_FOUND(404, "환자 정보가 존재하지 않습니다."),

    // RID
    RID_CONNECTION_TIMEOUT(408, "가명화 시스템에 연결할 수 없습니다."),
    CONVERT_RID_TO_PID_FAILED(500, "환자병록번호 변환에 실패했습니다."),
    ANN_PROCESS_FAILED(500, "가명화 처리에 실패했습니다."),

    // Record
    PID_NOT_FOUND_IN_SESSION(400, "연구별 환자 ID로 환자를 먼저 조회해 주세요."),

    // Form
    FAILED_GET_FORM_STYLE(500, "기록지 서식 정보를 불러오는 데 실패했습니다."),
    ;

    private final int status;
    private final String message;
}
