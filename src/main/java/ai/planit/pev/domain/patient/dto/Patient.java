package ai.planit.pev.domain.patient.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Patient {
    private String ptNo; // 가명화 환자 ID
    private String ptNm; // 환자명
    private String sexTpCd; // 성별
    private String ptBrdyDt; // 생년월일
}
