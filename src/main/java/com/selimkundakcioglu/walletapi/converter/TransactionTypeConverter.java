package com.selimkundakcioglu.walletapi.converter;

import com.selimkundakcioglu.walletapi.model.enumtype.TransactionType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

@Converter(autoApply = true)
public class TransactionTypeConverter implements AttributeConverter<TransactionType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TransactionType transactionType) {
        return Objects.isNull(transactionType) ? null : transactionType.getValue();
    }

    @Override
    public TransactionType convertToEntityAttribute(Integer value) {
        return Objects.isNull(value) ? null : TransactionType.of(value);
    }
}

