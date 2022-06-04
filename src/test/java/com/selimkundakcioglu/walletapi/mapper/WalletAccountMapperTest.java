package com.selimkundakcioglu.walletapi.mapper;

import com.selimkundakcioglu.walletapi.model.dto.TransactionDto;
import com.selimkundakcioglu.walletapi.model.dto.WalletAccountDto;
import com.selimkundakcioglu.walletapi.model.entity.Transaction;
import com.selimkundakcioglu.walletapi.model.entity.WalletAccount;
import com.selimkundakcioglu.walletapi.model.enumtype.Currency;
import com.selimkundakcioglu.walletapi.model.enumtype.TransactionType;
import com.selimkundakcioglu.walletapi.stubbuilder.TransactionStubBuilder;
import com.selimkundakcioglu.walletapi.stubbuilder.WalletAccountStubBuilder;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class WalletAccountMapperTest {

    private final WalletAccountMapper walletAccountMapper = new WalletAccountMapperImpl();

    @Test
    void toWalletAccountDto() {
        // given
        WalletAccount walletAccount = WalletAccountStubBuilder.getWalletAccount();

        // when
        WalletAccountDto walletAccountDto = walletAccountMapper.toWalletAccountDto(walletAccount);
        WalletAccountDto walletAccountDto2 = walletAccountMapper.toWalletAccountDto(null);

        // then
        assertThat(walletAccountDto.getBalance()).isEqualTo(BigDecimal.TEN);
        assertThat(walletAccountDto.getCurrency()).isEqualTo(Currency.TRY);

        assertThat(walletAccountDto2).isNull();
    }
}