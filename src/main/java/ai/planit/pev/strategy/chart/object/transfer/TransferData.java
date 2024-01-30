package ai.planit.pev.strategy.chart.object.transfer;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TransferData {
    List<TransferContent> contents;
    private String writerNm;
}
