package ai.planit.pev.domain.meta.record.service;

import ai.planit.pev.domain.meta.record.dao.MetaRecordDAO;
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
}
