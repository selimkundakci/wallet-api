package com.selimkundakcioglu.walletapi.stubbuilder;

import com.selimkundakcioglu.walletapi.model.entity.Transaction;
import com.selimkundakcioglu.walletapi.model.enumtype.TransactionType;

import java.math.BigDecimal;

public class TransactionStubBuilder {

    public static Transaction getTransaction() {
        return Transaction.builder()
                .transactionType(TransactionType.CHECKOUT)
                .referenceCode("referenceCode")
                .amount(BigDecimal.TEN)
                .build();
    }
}
