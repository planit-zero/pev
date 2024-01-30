package ai.planit.pev.domain.ods.checkout.dao;

import ai.planit.pev.strategy.chart.object.checkout.CheckoutContent;

import java.util.List;

public interface CheckoutDAO {
    List<CheckoutContent> getNrCheckoutContents(String keyId);

    String getNrCheckoutWriterText(String keyId);
}
