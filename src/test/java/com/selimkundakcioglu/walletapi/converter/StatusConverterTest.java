package com.selimkundakcioglu.walletapi.converter;

import com.selimkundakcioglu.walletapi.model.enumtype.Status;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StatusConverterTest {

    private final StatusConverter statusConverter = new StatusConverter();

    @Test
    void convertToDatabaseColumn() {
        Integer value = statusConverter.convertToDatabaseColumn(Status.ACTIVE);
        assertThat(value).isEqualTo(1);
        Integer value2 = statusConverter.convertToDatabaseColumn(Status.PASSIVE);
        assertThat(value2).isEqualTo(0);
        Integer value3 = statusConverter.convertToDatabaseColumn(Status.DELETED);
        assertThat(value3).isEqualTo(-1);
    }

    @Test
    void convertToEntityAttribute() {
        Status status = statusConverter.convertToEntityAttribute(1);
        assertThat(status).isEqualTo(Status.ACTIVE);
        Status status2 = statusConverter.convertToEntityAttribute(0);
        assertThat(status2).isEqualTo(Status.PASSIVE);
        Status status3 = statusConverter.convertToEntityAttribute(-11);
        assertThat(status3).isEqualTo(Status.DELETED);
    }
}