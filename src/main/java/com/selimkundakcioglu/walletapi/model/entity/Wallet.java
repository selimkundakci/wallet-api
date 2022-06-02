package com.selimkundakcioglu.walletapi.model.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Wallet extends BaseEntity {

    @Column(name = "user_id", updatable = false, nullable = false)
    private String userId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "wallet", cascade = CascadeType.PERSIST)
    private Set<WalletAccount> walletAccounts;
}
