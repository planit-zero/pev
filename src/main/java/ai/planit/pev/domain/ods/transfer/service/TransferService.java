package ai.planit.pev.domain.ods.transfer.service;

import ai.planit.pev.strategy.chart.object.transfer.TransferData;

public interface TransferService {
    TransferData getNrTransferData(String keyId);
}
