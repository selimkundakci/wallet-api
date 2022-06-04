package com.selimkundakcioglu.walletapi.model.request;

import com.selimkundakcioglu.walletapi.model.enumtype.Currency;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateWalletAccountRequest {

    private String userId;
    private Currency currency;
}
