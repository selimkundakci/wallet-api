package com.selimkundakcioglu.walletapi.stubbuilder;

import com.selimkundakcioglu.walletapi.model.entity.Wallet;
import com.selimkundakcioglu.walletapi.model.entity.WalletAccount;
import com.selimkundakcioglu.walletapi.model.enumtype.Currency;
import com.selimkundakcioglu.walletapi.model.enumtype.Status;
import com.selimkundakcioglu.walletapi.model.request.CreateWalletRequest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WalletStubBuilder {

    public static Wallet getWallet() {
        WalletAccount walletAccount = WalletAccountStubBuilder.getWalletAccount();
        Set<WalletAccount> walletAccounts = new HashSet<>(List.of(walletAccount));
        return Wallet.builder()
                .walletAccounts(walletAccounts)
                .userId("userId")
                .status(Status.ACTIVE)
                .build();
    }

    public static Wallet getEmptyWallet() {
        WalletAccount walletAccount = WalletAccountStubBuilder.getWalletAccount();
        return Wallet.builder()
                .userId("userId")
                .status(Status.ACTIVE)
                .build();
    }

    public static CreateWalletRequest getCreateWalletRequest() {
        return CreateWalletRequest.builder()
                .currency(Currency.TRY)
                .phoneNumber("5377079363")
                .userId("userId")
                .build();
    }
}
