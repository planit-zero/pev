package ai.planit.pev.strategy.chart.object.discharge;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DischargeData {
    List<DischargeContent> contents;
    private String writerNm;
}
