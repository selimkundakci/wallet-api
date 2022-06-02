package com.selimkundakcioglu.walletapi.constants;

import java.util.function.Supplier;

public class ExceptionCodes {

    public static final String ALREADY_AN_ACTIVE_WALLET_FOUND = "100";
    public static final String TRANSACTION_FOUND_WITH_SAME_REFERENCE_CODE = "200";
    public static final String CHECKOUT_BALANCE_NOT_ENOUGH = "201";
    public static final String ACCOUNT_NOT_FOUND = "202";
    public static final String WALLET_NOT_FOUND = "300";
    public static final String ACCOUNT_PRESENT_WITH_GIVEN_CURRENCY = "301";
}
