package ai.planit.pev.domain.ods.checkout.service;

import ai.planit.pev.strategy.chart.object.checkout.CheckoutData;

public interface CheckoutService {
    CheckoutData getNrCheckoutData(String keyId);
}
