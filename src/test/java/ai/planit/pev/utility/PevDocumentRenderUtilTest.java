package ai.planit.pev.utility;

import ai.planit.pev.domain.meta.record.service.MetaRecordService;
import ai.planit.pev.domain.ods.medical.service.MedicalService;
import ai.planit.pev.domain.ods.record.dto.Record;
import ai.planit.pev.strategy.chart.ChartContext;
import ai.planit.pev.strategy.chart.MedicalChartStrategy;
import ai.planit.pev.strategy.chart.object.common.ChartElement;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("local")
public class PevDocumentRenderUtilTest {
    @Autowired
    private MetaRecordService metaRecordService;

    @Autowired
    private MedicalService medicalService;

    @Test
    public void renderTest1() {
        Record.Response record = Record.Response.builder().build();
        ChartContext chartContext = new ChartContext();
        chartContext.setChartStrategy(new MedicalChartStrategy());

        List<ChartElement> format = metaRecordService.getRecordFormatList(record);
        Object dataSource = medicalService.getMedicalData(record);

        List<ChartElement> data = chartContext.getChartStrategy().getData(format, dataSource);

    }
}
