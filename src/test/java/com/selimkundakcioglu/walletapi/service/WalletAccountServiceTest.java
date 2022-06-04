package com.selimkundakcioglu.walletapi.service;

import com.selimkundakcioglu.walletapi.constants.ExceptionCodes;
import com.selimkundakcioglu.walletapi.exception.BusinessException;
import com.selimkundakcioglu.walletapi.model.entity.Wallet;
import com.selimkundakcioglu.walletapi.model.entity.WalletAccount;
import com.selimkundakcioglu.walletapi.model.enumtype.Currency;
import com.selimkundakcioglu.walletapi.model.enumtype.Status;
import com.selimkundakcioglu.walletapi.repository.WalletAccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WalletAccountServiceTest {

    @InjectMocks
    private WalletAccountService walletAccountService;

    @Mock
    private WalletAccountRepository walletAccountRepository;

    @Test
    void createAndSaveWalletAccount() {
        // given
        Currency currency = Currency.TRY;
        Wallet wallet = new Wallet();
        WalletAccount walletAccount = getWalletAccount(currency, wallet);

        when(walletAccountRepository.save(any(WalletAccount.class)))
                .thenReturn(walletAccount);

        // when
        WalletAccount response = walletAccountService.createAndSaveWalletAccount(currency, wallet);

        // then
        assertThat(response).isEqualTo(walletAccount);
    }

    @Test
    void getWalletAccountByUserIdAndCurrency() {
        // given
        String userId = "userId";
        Currency currency = Currency.TRY;
        WalletAccount mockWalletAccount = new WalletAccount();

        when(walletAccountRepository.findByWalletUserIdAndCurrency(userId, currency))
                .thenReturn(Optional.of(mockWalletAccount));

        // when
        WalletAccount response = walletAccountService.getWalletAccountByUserIdAndCurrency(userId, currency);

        // then
        assertThat(response).isEqualTo(mockWalletAccount);
    }

    @Test
    void getWalletAccountByUserIdAndCurrency_throwsException_when_walletAccountNotFound() {
        // given
        String userId = "userId";
        Currency currency = Currency.TRY;

        when(walletAccountRepository.findByWalletUserIdAndCurrency(userId, currency))
                .thenReturn(Optional.empty());

        // when
        Throwable throwable = catchThrowable(() -> walletAccountService.getWalletAccountByUserIdAndCurrency(userId, currency));

        // then
        assertThat(throwable).isNotNull().isExactlyInstanceOf(BusinessException.class);
        assertThat(((BusinessException) throwable).getCode()).isEqualTo(ExceptionCodes.ACCOUNT_NOT_FOUND);
    }

    private WalletAccount getWalletAccount(Currency currency, Wallet wallet) {
        return WalletAccount.builder()
                .currency(currency)
                .wallet(wallet)
                .balance(BigDecimal.ZERO)
                .status(Status.ACTIVE)
                .build();
    }
}