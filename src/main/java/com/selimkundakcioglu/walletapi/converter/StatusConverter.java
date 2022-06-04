package com.selimkundakcioglu.walletapi.converter;

import com.selimkundakcioglu.walletapi.model.enumtype.Status;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class StatusConverter implements AttributeConverter<Status, Integer> {
    public StatusConverter() {
    }

    public Integer convertToDatabaseColumn(Status status) {
        return status.getValue();
    }

    public Status convertToEntityAttribute(Integer value) {
        return switch (value) {
            case 1 -> Status.ACTIVE;
            case 0 -> Status.PASSIVE;
            default -> Status.DELETED;
        };
    }
}