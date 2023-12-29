package ai.planit.pev.strategy.chart.object.order;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderData {
    private List<OrderSection.Response> sections;
}
