package ai.planit.pev.strategy.chart;

import ai.planit.pev.strategy.chart.object.common.ChartElement;
import java.util.List;

public interface ChartStrategy {
    <T> List<ChartElement> getFormat(T source);

    <T> List<ChartElement> getData(T source);

    // 데이터 가명화
//    List<ChartElement> getMaskedData(List<ChartElement> origin);

    // 최종 차트 반환
}
