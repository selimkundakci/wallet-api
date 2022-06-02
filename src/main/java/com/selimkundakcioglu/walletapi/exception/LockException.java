package com.selimkundakcioglu.walletapi.exception;

import lombok.Getter;

@Getter
public class LockException extends RuntimeException {

    private final String errorMessage;

    public LockException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
}