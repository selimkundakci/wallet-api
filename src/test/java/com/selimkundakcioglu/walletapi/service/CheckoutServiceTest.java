package com.selimkundakcioglu.walletapi.service;

import com.selimkundakcioglu.walletapi.constants.ExceptionCodes;
import com.selimkundakcioglu.walletapi.exception.BusinessException;
import com.selimkundakcioglu.walletapi.mapper.TransactionMapper;
import com.selimkundakcioglu.walletapi.model.dto.TransactionDto;
import com.selimkundakcioglu.walletapi.model.entity.Transaction;
import com.selimkundakcioglu.walletapi.model.entity.WalletAccount;
import com.selimkundakcioglu.walletapi.model.enumtype.Currency;
import com.selimkundakcioglu.walletapi.model.enumtype.Status;
import com.selimkundakcioglu.walletapi.model.enumtype.TransactionType;
import com.selimkundakcioglu.walletapi.model.request.CheckoutRequest;
import com.selimkundakcioglu.walletapi.stubbuilder.CheckoutStubBuilder;
import com.selimkundakcioglu.walletapi.stubbuilder.WalletAccountStubBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CheckoutServiceTest {

    @InjectMocks
    private CheckoutService checkoutService;
    @Mock
    private WalletAccountService walletAccountService;
    @Mock
    private TransactionService transactionService;
    @Mock
    private TransactionMapper transactionMapper;

    private InOrder inOrder;

    @BeforeEach
    void setUp() {
        inOrder = Mockito.inOrder(walletAccountService, transactionService, transactionMapper);
    }

    @Test
    void checkout() {
        // given
        CheckoutRequest checkoutRequest = CheckoutStubBuilder.getCheckoutRequest();
        WalletAccount mockWalletAccount = WalletAccountStubBuilder.getWalletAccount();
        Transaction mockTransaction = getTransaction();
        TransactionDto mockTransactionDto = getTransactionDto();

        when(walletAccountService.getWalletAccountByUserIdAndCurrency(checkoutRequest.getUserId(), checkoutRequest.getCurrency()))
                .thenReturn(mockWalletAccount);
        when(transactionService.isPresentByReferenceCodeAndTransactionType(checkoutRequest.getReferenceId(), TransactionType.CHECKOUT))
                .thenReturn(false);
        when(transactionService.save(any(Transaction.class)))
                .thenReturn(mockTransaction);
        when(transactionMapper.toTransactionDto(any(Transaction.class)))
                .thenReturn(mockTransactionDto);

        // when
        TransactionDto transactionDto = checkoutService.checkout(checkoutRequest);

        // then
        assertThat(transactionDto).isEqualTo(mockTransactionDto);

        inOrder.verify(walletAccountService).getWalletAccountByUserIdAndCurrency(checkoutRequest.getUserId(), checkoutRequest.getCurrency());
        inOrder.verify(transactionService).isPresentByReferenceCodeAndTransactionType(checkoutRequest.getReferenceId(), TransactionType.CHECKOUT);
        inOrder.verify(transactionService).save(any(Transaction.class));
        inOrder.verify(walletAccountService).save(any(WalletAccount.class));
        inOrder.verify(transactionMapper).toTransactionDto(mockTransaction);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void checkout_throwsException_when_referenceCodeAlreadyExists() {
        // given
        CheckoutRequest checkoutRequest = CheckoutStubBuilder.getCheckoutRequest();
        WalletAccount mockWalletAccount = WalletAccountStubBuilder.getWalletAccount();

        when(walletAccountService.getWalletAccountByUserIdAndCurrency(checkoutRequest.getUserId(), checkoutRequest.getCurrency()))
                .thenReturn(mockWalletAccount);
        when(transactionService.isPresentByReferenceCodeAndTransactionType(checkoutRequest.getReferenceId(), TransactionType.CHECKOUT))
                .thenReturn(true);

        // when
        Throwable throwable = catchThrowable(() -> checkoutService.checkout(checkoutRequest));

        // then
        assertThat(throwable).isNotNull().isExactlyInstanceOf(BusinessException.class);
        assertThat(((BusinessException) throwable).getCode()).isEqualTo(ExceptionCodes.TRANSACTION_FOUND_WITH_SAME_REFERENCE_CODE);

        inOrder.verify(walletAccountService).getWalletAccountByUserIdAndCurrency(checkoutRequest.getUserId(), checkoutRequest.getCurrency());
        inOrder.verify(transactionService).isPresentByReferenceCodeAndTransactionType(checkoutRequest.getReferenceId(), TransactionType.CHECKOUT);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void checkout_throwsException_when_balanceNotEnough() {
        // given
        CheckoutRequest checkoutRequest = CheckoutStubBuilder.getCheckoutRequest();
        WalletAccount mockWalletAccount = WalletAccountStubBuilder.getWalletAccount();
        mockWalletAccount.setBalance(BigDecimal.ZERO);

        when(walletAccountService.getWalletAccountByUserIdAndCurrency(checkoutRequest.getUserId(), checkoutRequest.getCurrency()))
                .thenReturn(mockWalletAccount);
        when(transactionService.isPresentByReferenceCodeAndTransactionType(checkoutRequest.getReferenceId(), TransactionType.CHECKOUT))
                .thenReturn(false);

        // when
        Throwable throwable = catchThrowable(() -> checkoutService.checkout(checkoutRequest));

        // then
        assertThat(throwable).isNotNull().isExactlyInstanceOf(BusinessException.class);
        assertThat(((BusinessException) throwable).getCode()).isEqualTo(ExceptionCodes.CHECKOUT_BALANCE_NOT_ENOUGH);

        inOrder.verify(walletAccountService).getWalletAccountByUserIdAndCurrency(checkoutRequest.getUserId(), checkoutRequest.getCurrency());
        inOrder.verify(transactionService).isPresentByReferenceCodeAndTransactionType(checkoutRequest.getReferenceId(), TransactionType.CHECKOUT);
        inOrder.verifyNoMoreInteractions();
    }

    private Transaction getTransaction() {
        return Transaction.builder()
                .amount(BigDecimal.ONE)
                .transactionType(TransactionType.CHECKOUT)
                .referenceCode("referenceId")
                .status(Status.ACTIVE)
                .build();
    }
    private TransactionDto getTransactionDto() {
        return TransactionDto.builder()
                .transactionType(TransactionType.CHECKOUT)
                .amount(BigDecimal.ONE)
                .referenceCode("referenceId")
                .build();
    }
}