package ai.planit.pev.domain.form.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FormContentResponse {
    private List<FormSheet> sheets;
}
