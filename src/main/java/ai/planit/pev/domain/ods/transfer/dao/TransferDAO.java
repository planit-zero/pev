package ai.planit.pev.domain.ods.transfer.dao;

import ai.planit.pev.strategy.chart.object.transfer.TransferContent;

import java.util.List;

public interface TransferDAO {
    List<TransferContent> getNrTransferContents(String keyId);
    String getNrTransferWriterText(String keyId);
}
