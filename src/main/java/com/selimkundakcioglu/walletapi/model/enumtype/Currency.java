package com.selimkundakcioglu.walletapi.model.enumtype;

import java.util.Objects;
import java.util.stream.Stream;

public enum Currency {

    TRY("TRY"),
    USD("USD"),
    EUR("EUR");

    private final String value;

    Currency(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Currency of(String value) {
        return Stream.of(Currency.values())
                .filter(currency -> Objects.equals(currency.getValue(), value))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
