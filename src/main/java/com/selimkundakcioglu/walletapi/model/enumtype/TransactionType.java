package com.selimkundakcioglu.walletapi.model.enumtype;

import java.util.stream.Stream;

public enum TransactionType {

    CHECKOUT(0),
    TOP_UP(1),
    WITHDRAW(2),
    REFUND(3),
    REWARD(4),
    CANCEL_REWARD(5);

    private final int value;

    TransactionType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static TransactionType of(int value) {
        return Stream.of(TransactionType.values())
                .filter(transactionType -> transactionType.getValue() == value)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
