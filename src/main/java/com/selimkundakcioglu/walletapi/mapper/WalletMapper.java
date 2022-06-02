package com.selimkundakcioglu.walletapi.mapper;

import com.selimkundakcioglu.walletapi.model.dto.WalletDto;
import com.selimkundakcioglu.walletapi.model.entity.Wallet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = WalletAccountMapper.class)
public interface WalletMapper {

    @Mapping(source = "walletAccounts", target = "walletAccountDtos")
    WalletDto toWalletDto(Wallet wallet);
}
