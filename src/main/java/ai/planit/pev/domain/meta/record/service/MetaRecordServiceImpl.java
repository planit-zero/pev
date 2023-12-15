package ai.planit.pev.domain.meta.record.service;

import ai.planit.pev.domain.meta.record.dao.MetaRecordDAO;
import ai.planit.pev.domain.meta.record.dto.MetaRecordFormat;
import ai.planit.pev.domain.meta.record.dto.MetaRecordList;
import ai.planit.pev.domain.ods.record.dto.Record;
import ai.planit.pev.strategy.chart.object.common.ChartElement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MetaRecordServiceImpl implements MetaRecordService {
    private final MetaRecordDAO metaRecordDAO;

    @Override
    public MetaRecordList getMetaRecordList() {
        MetaRecordList metaRecordList = new MetaRecordList();
        metaRecordList.setMetaRecords(metaRecordDAO.getMetaRecordList());
        return metaRecordList;
    }

    @Override
    public List<ChartElement> getRecordFormatList(Record.Response record) {
        return metaRecordDAO.getRecordFormatList(record);
    }

    @Override
    public void reloadMedicalRecordFormat(List<MetaRecordFormat.Response> metaRecordFormatList) {
        final double LOOP_RANGE = 1000.0;
        int loopCount = (int) Math.ceil(metaRecordFormatList.size() / LOOP_RANGE);

        if (loopCount == 0) return;

        for (int i = 1; i <= loopCount; i++) {
            int fromIndex = (i - 1) * (int) LOOP_RANGE;
            int toIndex = (i * (int) LOOP_RANGE);

            if (i == loopCount) toIndex = metaRecordFormatList.size();

            List<MetaRecordFormat.Response> subList = metaRecordFormatList.subList(fromIndex, toIndex);
            metaRecordDAO.reloadMedicalRecordFormat(subList);
        }
    }
}
