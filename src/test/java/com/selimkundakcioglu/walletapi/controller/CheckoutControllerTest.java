package com.selimkundakcioglu.walletapi.controller;

import com.selimkundakcioglu.walletapi.lock.LockableService;
import com.selimkundakcioglu.walletapi.model.dto.TransactionDto;
import com.selimkundakcioglu.walletapi.model.request.CheckoutRequest;
import com.selimkundakcioglu.walletapi.model.response.Response;
import com.selimkundakcioglu.walletapi.model.response.ResponseFactory;
import com.selimkundakcioglu.walletapi.service.CheckoutService;
import com.selimkundakcioglu.walletapi.stubbuilder.CheckoutStubBuilder;
import com.selimkundakcioglu.walletapi.stubbuilder.MockFencedLock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CheckoutControllerTest {

    @InjectMocks
    private CheckoutController checkoutController;
    @Mock
    private ResponseFactory responseFactory;
    @Mock
    private CheckoutService checkoutService;
    @Mock
    private LockableService lockableService;

    private InOrder inOrder;

    @BeforeEach
    void setUp() {
        inOrder = Mockito.inOrder(responseFactory,
                checkoutService,
                lockableService);
    }

    @Test
    void checkout() {
        // given
        CheckoutRequest checkoutRequest = CheckoutStubBuilder.getCheckoutRequest();
        MockFencedLock mockLock = new MockFencedLock();
        TransactionDto mockTransactionDto = TransactionDto.builder().build();
        Response<TransactionDto> mockResponse = new Response<>();

        when(lockableService.tryLock(checkoutRequest.getUserId(), "checkout"))
                .thenReturn(mockLock);
        when(checkoutService.checkout(checkoutRequest)).thenReturn(mockTransactionDto);
        when(responseFactory.response(mockTransactionDto)).thenReturn(mockResponse);

        // when
        Response<TransactionDto> checkout = checkoutController.checkout(checkoutRequest);

        // then
        assertThat(checkout).isEqualTo(mockResponse);

        inOrder.verify(lockableService).tryLock(checkoutRequest.getUserId(), "checkout");
        inOrder.verify(checkoutService).checkout(checkoutRequest);
        inOrder.verify(responseFactory).response(mockTransactionDto);
        inOrder.verify(lockableService).unlock(mockLock, "checkout", checkoutRequest.getUserId());
        inOrder.verifyNoMoreInteractions();
    }
}