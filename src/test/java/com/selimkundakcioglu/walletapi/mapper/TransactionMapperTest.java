package com.selimkundakcioglu.walletapi.mapper;

import com.selimkundakcioglu.walletapi.model.dto.TransactionDto;
import com.selimkundakcioglu.walletapi.model.entity.Transaction;
import com.selimkundakcioglu.walletapi.model.enumtype.Status;
import com.selimkundakcioglu.walletapi.model.enumtype.TransactionType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class TransactionMapperTest {

    private final TransactionMapper transactionMapper = new TransactionMapperImpl();

    @Test
    void toTransactionDto() {
        // given
        Transaction transaction = Transaction.builder()
                .transactionType(TransactionType.CHECKOUT)
                .referenceCode("referenceCode")
                .amount(BigDecimal.TEN)
                .build();

        // when
        TransactionDto transactionDto = transactionMapper.toTransactionDto(transaction);
        TransactionDto transactionDto2 = transactionMapper.toTransactionDto(null);

        // then
        assertThat(transactionDto.getTransactionType()).isEqualTo(TransactionType.CHECKOUT);
        assertThat(transactionDto.getAmount()).isEqualTo(BigDecimal.TEN);
        assertThat(transactionDto.getReferenceCode()).isEqualTo("referenceCode");

        assertThat(transactionDto2).isNull();
    }
}