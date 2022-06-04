package com.selimkundakcioglu.walletapi.controller;

import com.selimkundakcioglu.walletapi.model.dto.WalletDto;
import com.selimkundakcioglu.walletapi.model.request.CreateWalletAccountRequest;
import com.selimkundakcioglu.walletapi.model.request.CreateWalletRequest;
import com.selimkundakcioglu.walletapi.model.response.Response;
import com.selimkundakcioglu.walletapi.model.response.ResponseFactory;
import com.selimkundakcioglu.walletapi.service.WalletService;
import com.selimkundakcioglu.walletapi.stubbuilder.WalletAccountStubBuilder;
import com.selimkundakcioglu.walletapi.stubbuilder.WalletStubBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WalletControllerTest {

    @InjectMocks
    private WalletController walletController;
    @Mock
    private ResponseFactory responseFactory;
    @Mock
    private WalletService walletService;

    @Test
    void createWallet() {
        // given
        CreateWalletRequest createWalletRequest = WalletStubBuilder.getCreateWalletRequest();
        WalletDto mockWalletDto = WalletDto.builder().build();
        Response mockResponse = Response.builder().data(mockWalletDto).build();

        when(walletService.createWallet(createWalletRequest)).thenReturn(mockWalletDto);
        when(responseFactory.response(mockWalletDto)).thenReturn(mockResponse);

        // when
        Response<WalletDto> response = walletController.createWallet(createWalletRequest);

        // then
        assertThat(response).isEqualTo(mockResponse);
    }

    @Test
    void createWalletAccount() {
        // given
        CreateWalletAccountRequest createWalletAccountRequest = WalletAccountStubBuilder.getCreateWalletAccountRequest();
        WalletDto mockWalletDto = WalletDto.builder().build();
        Response mockResponse = Response.builder().data(mockWalletDto).build();

        when(walletService.createWalletAccount(createWalletAccountRequest)).thenReturn(mockWalletDto);
        when(responseFactory.response(mockWalletDto)).thenReturn(mockResponse);

        // when
        Response<WalletDto> response = walletController.createWalletAccount(createWalletAccountRequest);

        // then
        assertThat(response).isEqualTo(mockResponse);
    }

    @Test
    void queryWallet() {
        // given
        String userId = "userId";
        WalletDto mockWalletDto = WalletDto.builder().build();
        Response mockResponse = Response.builder().data(mockWalletDto).build();

        when(walletService.queryWallet(userId)).thenReturn(mockWalletDto);
        when(responseFactory.response(mockWalletDto)).thenReturn(mockResponse);

        // when
        Response<WalletDto> response = walletController.queryWallet(userId);

        // then
        assertThat(response).isEqualTo(mockResponse);
    }
}