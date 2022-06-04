package com.selimkundakcioglu.walletapi.exception;

import lombok.Getter;

@Getter
public class LockException extends BaseException {

    public LockException(String code, Object... args) {
        super(code, args);
    }
}