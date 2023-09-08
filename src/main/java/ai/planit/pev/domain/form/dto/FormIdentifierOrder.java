package ai.planit.pev.domain.form.dto;

import ai.planit.pev.domain.record.dto.RecordIdentifier;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormIdentifierOrder {
    private String ptNo;
    private String ordDt;
    private String medPactTpCd;

    public void convertFromRecordIdentifier(RecordIdentifier identifier) {
        try {
            String[] keyIdArr = identifier.getKeyId().split("_");

            this.ptNo = keyIdArr[0];
            this.ordDt = keyIdArr[1];
            this.medPactTpCd = keyIdArr[2];
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
