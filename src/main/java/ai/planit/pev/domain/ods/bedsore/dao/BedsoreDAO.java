package ai.planit.pev.domain.ods.bedsore.dao;

import ai.planit.pev.strategy.chart.object.bedsore.BedsoreContent;

import java.util.List;

public interface BedsoreDAO {
    List<BedsoreContent> getBedsoreContents(String keyId);
}
