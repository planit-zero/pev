package ai.planit.pev.domain.ods.bedsore.dao;

import ai.planit.pev.strategy.chart.object.bedsore.BedsoreContent;

import java.util.List;

public interface BedsoreDAO {
    List<BedsoreContent> getBedsoreContents(String keyId);

    List<String> getBedsoreEvaluationDetailTextList(String keyId);

    String getBedsoreEvaluationTotalText(String keyId);

    List<String> getBedsoreEvaluationPreventTextList(String keyId);

    String getBedsoreEvaluationYnText(String keyId);

    String getBedsoreEvaluationWriterText(String keyId);
}
