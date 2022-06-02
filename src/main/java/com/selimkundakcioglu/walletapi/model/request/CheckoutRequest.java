package com.selimkundakcioglu.walletapi.model.request;

import com.selimkundakcioglu.walletapi.model.enumtype.Currency;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CheckoutRequest {

    private String userId;
    private String referenceId;
    private BigDecimal amount;
    private Currency currency;
}
