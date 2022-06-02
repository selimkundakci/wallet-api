package com.selimkundakcioglu.walletapi.model.entity;

import com.selimkundakcioglu.walletapi.model.enumtype.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class WalletAccount extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id", referencedColumnName = "id", nullable = false)
    private Wallet wallet;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "currency")
    private Currency currency;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "walletAccount", cascade = CascadeType.PERSIST)
    private Set<Transaction> transactions;
}
