package ai.planit.pev.strategy.chart.object.checkout;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CheckoutData {
    private List<CheckoutContent> contents;
    private String writerNm;
}
