package com.selimkundakcioglu.walletapi.stubbuilder;

import com.selimkundakcioglu.walletapi.model.enumtype.Currency;
import com.selimkundakcioglu.walletapi.model.request.CheckoutRequest;

import java.math.BigDecimal;

public class CheckoutStubBuilder {

    public static CheckoutRequest getCheckoutRequest() {
        return CheckoutRequest.builder()
                .amount(BigDecimal.ONE)
                .currency(Currency.TRY)
                .referenceId("referenceId")
                .userId("userId")
                .build();
    }

}
