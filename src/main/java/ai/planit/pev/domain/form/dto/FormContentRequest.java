package ai.planit.pev.domain.form.dto;

import ai.planit.pev.domain.record.dto.RecordIdentifier;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FormContentRequest {
    private List<RecordIdentifier> identifiers;
}
