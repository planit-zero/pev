package ai.planit.pev.domain.ods.transfer.service;

import ai.planit.pev.domain.ods.transfer.dao.TransferDAO;
import ai.planit.pev.strategy.chart.object.transfer.TransferData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {
    private final TransferDAO transferDAO;

    @Override
    public TransferData getNrTransferData(String keyId) {
        TransferData transferData = new TransferData();

        transferData.setContents(transferDAO.getNrTransferContents(keyId));
        transferData.setWriterNm(transferDAO.getNrTransferWriterText(keyId));

        return transferData;
    }
}
