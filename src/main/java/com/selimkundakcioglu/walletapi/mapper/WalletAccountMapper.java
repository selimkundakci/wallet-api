package com.selimkundakcioglu.walletapi.mapper;

import com.selimkundakcioglu.walletapi.model.dto.WalletAccountDto;
import com.selimkundakcioglu.walletapi.model.entity.WalletAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {TransactionMapper.class})
public interface WalletAccountMapper {

    @Mapping(source = "transactions", target = "transactionDtos")
    WalletAccountDto toWalletAccountDto(WalletAccount walletAccount);
}
