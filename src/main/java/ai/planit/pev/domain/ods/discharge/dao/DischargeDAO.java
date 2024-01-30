package ai.planit.pev.domain.ods.discharge.dao;

import ai.planit.pev.strategy.chart.object.discharge.DischargeContent;

import java.util.List;

public interface DischargeDAO {
    List<DischargeContent> getNrDischargeContents(String keyId);

    String getNrDischargeWriterText(String keyId);
}
