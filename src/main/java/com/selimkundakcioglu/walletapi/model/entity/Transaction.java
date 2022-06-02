package com.selimkundakcioglu.walletapi.model.entity;

import com.selimkundakcioglu.walletapi.converter.TransactionTypeConverter;
import com.selimkundakcioglu.walletapi.model.enumtype.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class Transaction extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_account_id", referencedColumnName = "id", nullable = false)
    private WalletAccount walletAccount;

    @Column(name = "amount", updatable = false, nullable = false, precision = 9, scale = 2)
    private BigDecimal amount;

    @Convert(converter = TransactionTypeConverter.class)
    @Column(name = "transaction_type", updatable = false, nullable = false, columnDefinition = "INTEGER CHECK (transaction_type IN (0, 1, 2, 3, 4, 5))")
    private TransactionType transactionType;

    @Column(name = "reference_code", updatable = false)
    private String referenceCode;


}
