package ai.planit.pev.strategy.chart.object.pathology;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PathologyProcess {
    private String pthlProTpCd; // 병리처리구분코드
    private String pthlProTpNm; // 병리처리구분명
    private String wkFmtStfNm; // 작업수행직원명
}
