package ai.planit.pev.domain.ods.medical.service;

import ai.planit.pev.domain.meta.record.dto.MetaRecordFormat;
import ai.planit.pev.domain.ods.record.dto.Record;
import ai.planit.pev.strategy.chart.object.common.ChartStyleSection;
import ai.planit.pev.strategy.chart.object.common.ChartStyleXml;
import ai.planit.pev.strategy.chart.object.medical.MedicalData;
import ai.planit.pev.strategy.chart.object.medical.MedicalReply;

import java.util.List;

public interface MedicalService {
    List<MedicalData> getMedicalData(Record.Response record);

    List<MetaRecordFormat.Response> getMedicalRecordFormatList(MetaRecordFormat.Request request);

    List<ChartStyleSection> getChartStyleSections(ChartStyleXml.Request request);

    Record.Response getMedicalReplyRecord(MedicalReply.Request request);
}
