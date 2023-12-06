package ai.planit.pev.strategy.chart.object.pathology;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PathologyContent {
    private String plrtLdat; // 병리결과자료
    private String acptDt; // 접수일
    private String lshDt; // 판독일 (추후 검증 필요)
}
