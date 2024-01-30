package ai.planit.pev.domain.ods.checkout.service;

import ai.planit.pev.domain.ods.checkout.dao.CheckoutDAO;
import ai.planit.pev.strategy.chart.object.checkout.CheckoutData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {
    private final CheckoutDAO checkoutDAO;

    @Override
    public CheckoutData getNrCheckoutData(String keyId) {
        CheckoutData checkoutData = new CheckoutData();
        checkoutData.setContents(checkoutDAO.getNrCheckoutContents(keyId));
        checkoutData.setWriterNm(checkoutDAO.getNrCheckoutWriterText(keyId));

        return checkoutData;
    }
}
