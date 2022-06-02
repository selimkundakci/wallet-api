package com.selimkundakcioglu.walletapi.controller;

import com.selimkundakcioglu.walletapi.model.dto.TransactionDto;
import com.selimkundakcioglu.walletapi.model.dto.WalletDto;
import com.selimkundakcioglu.walletapi.model.request.TopUpRequest;
import com.selimkundakcioglu.walletapi.model.response.Response;
import com.selimkundakcioglu.walletapi.model.response.ResponseFactory;
import com.selimkundakcioglu.walletapi.service.TopUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/v1/topup")
@RequiredArgsConstructor
public class TopUpController {

    private final ResponseFactory responseFactory;
    private final TopUpService topUpService;

    @PostMapping()
    @ResponseStatus(CREATED)
    public Response<TransactionDto> topUp(@RequestBody TopUpRequest topUpRequest) {
        TransactionDto transactionDto = topUpService.topUp(topUpRequest);
        return responseFactory.response(transactionDto);
    }
}
