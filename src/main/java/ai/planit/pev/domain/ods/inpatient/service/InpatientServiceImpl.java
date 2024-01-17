package ai.planit.pev.domain.ods.inpatient.service;

import ai.planit.pev.domain.ods.inpatient.dao.InpatientDAO;
import ai.planit.pev.domain.ods.record.dto.Record;
import ai.planit.pev.strategy.chart.object.common.ChartElement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InpatientServiceImpl implements InpatientService {
    private final InpatientDAO inpatientDAO;

    @Override
    public List<ChartElement> getInpatientFormat(Record.Response record) {
        List<ChartElement> format = new ArrayList<>();

        List<ChartElement> rawEntities = inpatientDAO.getNrInpatientEntities(record.getKeyId());
        List<ChartElement> distinctEntities = new ArrayList<>();

        for (ChartElement rawEntity : rawEntities) {
            Optional<ChartElement> entity = distinctEntities
                    .stream()
                    .filter(e -> e.getId().equals(rawEntity.getId()))
                    .findFirst();

            if (entity.isEmpty()) distinctEntities.add(rawEntity);
        }


        format.addAll(distinctEntities);

        List<ChartElement> rawAttributes = inpatientDAO.getNrInpatientAttributes(record.getKeyId());
        List<ChartElement> distinctAttributes = new ArrayList<>();

        for (ChartElement rawAttribute : rawAttributes) {
            Optional<ChartElement> attribute = distinctAttributes
                    .stream()
                    .filter(e -> e.getId().equals(rawAttribute.getId()) && e.getParentId().equals(rawAttribute.getParentId()))
                    .findFirst();

            if (attribute.isEmpty()) distinctAttributes.add(rawAttribute);
        }

        format.addAll(distinctAttributes);

        format.addAll(inpatientDAO.getNrInpatientValues(record.getKeyId()));

        return format;
    }
}
