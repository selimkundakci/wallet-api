package com.selimkundakcioglu.walletapi.controller;

import com.selimkundakcioglu.walletapi.model.dto.TransactionDto;
import com.selimkundakcioglu.walletapi.model.request.TopUpRequest;
import com.selimkundakcioglu.walletapi.model.response.Response;
import com.selimkundakcioglu.walletapi.model.response.ResponseFactory;
import com.selimkundakcioglu.walletapi.service.TopUpService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TopUpControllerTest {

    @InjectMocks
    private TopUpController topUpController;
    @Mock
    private ResponseFactory responseFactory;
    @Mock
    private TopUpService topUpService;

    @Test
    void topUp() {
        // given
        TopUpRequest topUpRequest = TopUpRequest.builder().build();
        TransactionDto mockTransactionDto = TransactionDto.builder().build();
        Response<TransactionDto> mockResponse = new Response<>();

        when(topUpService.topUp(topUpRequest)).thenReturn(mockTransactionDto);
        when(responseFactory.response(mockTransactionDto)).thenReturn(mockResponse);

        // when
        Response<TransactionDto> response = topUpController.topUp(topUpRequest);

        // then
        assertThat(response).isEqualTo(mockResponse);
    }
}