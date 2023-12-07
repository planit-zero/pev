package ai.planit.pev.domain.meta.record.dao;

import ai.planit.pev.domain.ods.record.dto.Record;
import ai.planit.pev.strategy.chart.object.common.ChartElement;

import java.util.List;

public interface MetaRecordDAO {
    List<ChartElement> getRecordFormatList(Record.Response record);
}
