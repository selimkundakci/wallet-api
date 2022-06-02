package com.selimkundakcioglu.walletapi.model.dto;

import com.selimkundakcioglu.walletapi.model.enumtype.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class TransactionDto {

    private BigDecimal amount;
    private TransactionType transactionType;
    private String referenceCode;
}
