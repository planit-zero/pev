package ai.planit.pev.domain.form.dao;

import ai.planit.pev.domain.form.dto.FormFixedSectionItemsInSurgery;
import ai.planit.pev.domain.form.dto.FormIdentifier;

import java.util.List;

public interface FormFixedSectionDAO {
    List<FormFixedSectionItemsInSurgery> getFixedSectionItemsInSurgery(FormIdentifier identifier);
}
