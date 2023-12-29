package ai.planit.pev.domain.ods.medical.dao;

import ai.planit.pev.domain.meta.record.dto.MetaRecordFormat;
import ai.planit.pev.domain.ods.record.dto.Record;
import ai.planit.pev.strategy.chart.object.common.ChartStyleXml;
import ai.planit.pev.strategy.chart.object.medical.MedicalData;
import ai.planit.pev.strategy.chart.object.medical.MedicalReply;

import java.util.List;

public interface MedicalDAO {
    List<MedicalData> getMedicalData(Record.Response record);
    List<MetaRecordFormat.Response> getMedicalRecordFormatList(MetaRecordFormat.Request request);
    List<ChartStyleXml.Response> getChartStyleList(ChartStyleXml.Request request);

    Record.Response getMedicalReplyRecord(MedicalReply.Request request);
}
