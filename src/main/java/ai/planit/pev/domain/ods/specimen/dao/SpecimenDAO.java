package ai.planit.pev.domain.ods.specimen.dao;

import ai.planit.pev.strategy.chart.object.specimen.SpecimenInfo;
import ai.planit.pev.strategy.chart.object.specimen.SpecimenRequest;
import ai.planit.pev.strategy.chart.object.specimen.SpecimenResult;

import java.util.List;

public interface SpecimenDAO {
    SpecimenInfo getSpecimenInfo(SpecimenRequest request);
    List<SpecimenResult> getSpecimenResults(SpecimenRequest request);
}
