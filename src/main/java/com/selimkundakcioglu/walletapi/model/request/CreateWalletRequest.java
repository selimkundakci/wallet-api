package com.selimkundakcioglu.walletapi.model.request;

import com.selimkundakcioglu.walletapi.model.enumtype.Currency;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateWalletRequest {

    private String phoneNumber;
    private String userId;
    private Currency currency;
}
