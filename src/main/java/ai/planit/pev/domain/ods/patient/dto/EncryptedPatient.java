package ai.planit.pev.domain.ods.patient.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EncryptedPatient {
    private final String hspTpCd = "01";
    private String gid;
    private String rid;
}
