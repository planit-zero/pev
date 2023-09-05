package ai.planit.pev.domain.form.service;

import ai.planit.pev.domain.form.dto.FormContentRequest;
import ai.planit.pev.domain.form.dto.FormContentResponse;

public interface FormService {
    FormContentResponse getFormContent(FormContentRequest formContentRequest);
}
