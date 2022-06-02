package com.selimkundakcioglu.walletapi.service;

import com.selimkundakcioglu.walletapi.constants.ExceptionCodes;
import com.selimkundakcioglu.walletapi.exception.BusinessException;
import com.selimkundakcioglu.walletapi.model.entity.Wallet;
import com.selimkundakcioglu.walletapi.model.entity.WalletAccount;
import com.selimkundakcioglu.walletapi.model.enumtype.Currency;
import com.selimkundakcioglu.walletapi.model.enumtype.Status;
import com.selimkundakcioglu.walletapi.repository.WalletAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class WalletAccountService {

    private final WalletAccountRepository walletAccountRepository;

    public WalletAccount createWalletAccount(Currency currency) {
        return WalletAccount.builder()
                .currency(currency)
                .balance(BigDecimal.ZERO)
                .status(Status.ACTIVE)
                .build();
    }

    public WalletAccount getWalletAccountByUserIdAndCurrency(String userId, Currency currency) {
        return walletAccountRepository.findByWalletUserIdAndCurrency(userId, currency)
                .orElseThrow(() -> new BusinessException(ExceptionCodes.ACCOUNT_NOT_FOUND));
    }

    public void save(WalletAccount walletAccount) {
        walletAccountRepository.save(walletAccount);
    }

    public WalletAccount createAndSaveWalletAccount(Currency currency, Wallet wallet) {
        WalletAccount walletAccount = createWalletAccount(currency);
        walletAccount.setWallet(wallet);
        return walletAccountRepository.save(walletAccount);
    }
}
