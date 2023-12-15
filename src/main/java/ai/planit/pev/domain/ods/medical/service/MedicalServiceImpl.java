package ai.planit.pev.domain.ods.medical.service;

import ai.planit.pev.domain.meta.record.dto.MetaRecordFormat;
import ai.planit.pev.domain.ods.medical.dao.MedicalDAO;
import ai.planit.pev.domain.ods.record.dto.Record;
import ai.planit.pev.strategy.chart.object.medical.MedicalData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalServiceImpl implements MedicalService {
    private final MedicalDAO medicalDAO;

    @Override
    public List<MedicalData> getMedicalData(Record.Response record) {
        return medicalDAO.getMedicalData(record);
    }

    @Override
    public List<MetaRecordFormat.Response> getMedicalRecordFormatList(MetaRecordFormat.Request request) {
        return medicalDAO.getMedicalRecordFormatList(request);
    }
}
