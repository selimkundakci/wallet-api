package com.selimkundakcioglu.walletapi.model.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ExceptionResponse {

    private String code;
    private String message;
}
