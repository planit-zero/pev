package ai.planit.pev.domain.ods.cpr.dao;

import ai.planit.pev.domain.ods.record.dto.Record;
import ai.planit.pev.strategy.chart.object.cpr.CprData;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CprDAO {

    private final SqlSessionTemplate sqlSessionTemplate;

    public List<CprData> getCprData(Record.Response record) {
        return sqlSessionTemplate.selectList("getCprData", record);
    }

}
