package com.selimkundakcioglu.walletapi.converter;

import com.selimkundakcioglu.walletapi.model.enumtype.TransactionType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TransactionTypeConverterTest {

    private final TransactionTypeConverter transactionTypeConverter = new TransactionTypeConverter();

    @Test
    void convertToDatabaseColumn() {
        // given
        // when
        Integer value = transactionTypeConverter.convertToDatabaseColumn(TransactionType.CHECKOUT);

        // then
        assertThat(value).isEqualTo(0);
    }

    @Test
    void convertToEntityAttribute() {
        // given
        // when
        TransactionType transactionType = transactionTypeConverter.convertToEntityAttribute(1);

        // then
        assertThat(transactionType).isEqualTo(TransactionType.TOP_UP);
    }
}