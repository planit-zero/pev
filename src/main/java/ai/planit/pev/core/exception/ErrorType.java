package ai.planit.pev.core.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorType {
    // Patient
    GID_NOT_FOUND(400, "가명화 환자 ID가 입력되지 않았습니다."),
    PID_NOT_FOUND(404, "입력한 가명화 환자 ID에 해당하는 환자병록번호가 존재하지 않습니다."),
    PATIENT_NOT_FOUND(404, "환자 정보가 존재하지 않습니다."),

    // RID
    RID_CONNECTION_TIMEOUT(408, "가명화 시스템에 연결할 수 없습니다."),
    CONVERT_GID_TO_PID_FAILED(500, "환자병록번호 변환에 실패했습니다.")
    ;

    private final int status;
    private final String message;
}
