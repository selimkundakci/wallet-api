package com.selimkundakcioglu.walletapi.model.request;

import com.selimkundakcioglu.walletapi.model.enumtype.Currency;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class TopUpRequest {

    private String userId;
    private BigDecimal amount;
    private Currency currency;
}
