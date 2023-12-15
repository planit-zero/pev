package ai.planit.pev.domain.meta.record.service;

import ai.planit.pev.domain.meta.record.dto.MetaRecordFormat;
import ai.planit.pev.domain.meta.record.dto.MetaRecordList;
import ai.planit.pev.domain.ods.record.dto.Record;
import ai.planit.pev.strategy.chart.object.common.ChartElement;

import java.util.List;

public interface MetaRecordService {
    MetaRecordList getMetaRecordList();
    List<ChartElement> getRecordFormatList(Record.Response record);

    void reloadMedicalRecordFormat(List<MetaRecordFormat.Response> metaRecordFormatList);
}
