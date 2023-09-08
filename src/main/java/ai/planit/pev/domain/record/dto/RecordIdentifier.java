package ai.planit.pev.domain.record.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecordIdentifier {
    private String recordType;
    private String recordDetailType;
    private String keyId;
}
