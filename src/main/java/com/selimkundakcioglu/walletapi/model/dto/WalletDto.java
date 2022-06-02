package com.selimkundakcioglu.walletapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class WalletDto {

    private String userId;
    private Set<WalletAccountDto> walletAccountDtos;
}
