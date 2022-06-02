package com.selimkundakcioglu.walletapi.controller;

import com.selimkundakcioglu.walletapi.model.dto.WalletDto;
import com.selimkundakcioglu.walletapi.model.request.CreateWalletAccountRequest;
import com.selimkundakcioglu.walletapi.model.request.CreateWalletRequest;
import com.selimkundakcioglu.walletapi.model.response.Response;
import com.selimkundakcioglu.walletapi.model.response.ResponseFactory;
import com.selimkundakcioglu.walletapi.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/wallet")
public class WalletController {

    private final ResponseFactory responseFactory;
    private final WalletService walletService;

    @PostMapping
    @ResponseStatus(CREATED)
    public Response<WalletDto> createWallet(@RequestBody CreateWalletRequest createWalletRequest) {
        WalletDto walletDto = walletService.createWallet(createWalletRequest);
        return responseFactory.response(walletDto);
    }

    @PostMapping("/account")
    @ResponseStatus(CREATED)
    public Response<WalletDto> createWalletAccount(@RequestBody CreateWalletAccountRequest createWalletAccountRequest) {
        WalletDto walletDto = walletService.createWalletAccount(createWalletAccountRequest);
        return responseFactory.response(walletDto);
    }

    @GetMapping("/{userId}")
    @ResponseStatus(CREATED)
    public Response<WalletDto> queryWallet(@PathVariable String userId) {
        WalletDto walletDto = walletService.queryWallet(userId);
        return responseFactory.response(walletDto);
    }


}
