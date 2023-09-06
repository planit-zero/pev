package ai.planit.pev.domain.form.service;

import ai.planit.pev.domain.form.dto.FormIdentifier;
import ai.planit.pev.domain.form.dto.FormSection;

public interface FormFixedSectionService {
    FormSection getFixedSection(FormIdentifier identifier, FormSection section);
}
