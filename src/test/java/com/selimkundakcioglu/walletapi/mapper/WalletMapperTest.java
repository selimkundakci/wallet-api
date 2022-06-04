package com.selimkundakcioglu.walletapi.mapper;

import com.selimkundakcioglu.walletapi.model.dto.WalletDto;
import com.selimkundakcioglu.walletapi.model.entity.Wallet;
import com.selimkundakcioglu.walletapi.stubbuilder.WalletStubBuilder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WalletMapperTest {

    private final WalletMapper walletMapper = new WalletMapperImpl();

    @Test
    void toWalletAccountDto() {
        // given
        Wallet wallet = WalletStubBuilder.getEmptyWallet();

        // when
        WalletDto walletDto = walletMapper.toWalletDto(wallet);
        WalletDto walletDto2 = walletMapper.toWalletDto(null);

        // then
        assertThat(walletDto.getUserId()).isEqualTo("userId");

        assertThat(walletDto2).isNull();
    }

}