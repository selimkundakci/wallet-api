package com.selimkundakcioglu.walletapi.stubbuilder;

import com.selimkundakcioglu.walletapi.model.entity.WalletAccount;
import com.selimkundakcioglu.walletapi.model.enumtype.Currency;
import com.selimkundakcioglu.walletapi.model.enumtype.Status;
import com.selimkundakcioglu.walletapi.model.request.CreateWalletAccountRequest;

import java.math.BigDecimal;

public class WalletAccountStubBuilder {

    public static WalletAccount getWalletAccount() {
        return WalletAccount.builder()
                .status(Status.ACTIVE)
                .balance(BigDecimal.TEN)
                .currency(Currency.TRY)
                .build();
    }

    public static WalletAccount getWalletAccount(Currency currency) {
        return WalletAccount.builder()
                .status(Status.ACTIVE)
                .balance(BigDecimal.TEN)
                .currency(currency)
                .build();
    }

    public static CreateWalletAccountRequest getCreateWalletAccountRequest(Currency currency) {
        return CreateWalletAccountRequest.builder()
                .currency(currency)
                .userId("userId")
                .build();
    }

    public static CreateWalletAccountRequest getCreateWalletAccountRequest() {
        return CreateWalletAccountRequest.builder()
                .currency(Currency.TRY)
                .userId("userId")
                .build();
    }
}
