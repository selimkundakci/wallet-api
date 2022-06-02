package com.selimkundakcioglu.walletapi.model.dto;

import com.selimkundakcioglu.walletapi.model.enumtype.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class WalletAccountDto {

    private BigDecimal balance;
    private Currency currency;
    private Set<TransactionDto> transactionDtos;
}
