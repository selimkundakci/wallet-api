package com.selimkundakcioglu.walletapi.service;

import com.selimkundakcioglu.walletapi.mapper.TransactionMapper;
import com.selimkundakcioglu.walletapi.model.dto.TransactionDto;
import com.selimkundakcioglu.walletapi.model.entity.Transaction;
import com.selimkundakcioglu.walletapi.model.entity.WalletAccount;
import com.selimkundakcioglu.walletapi.model.enumtype.Currency;
import com.selimkundakcioglu.walletapi.model.enumtype.Status;
import com.selimkundakcioglu.walletapi.model.request.TopUpRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TopUpServiceTest {

    @InjectMocks
    private TopUpService topUpService;
    @Mock
    private WalletAccountService walletAccountService;
    @Mock
    private TransactionService transactionService;
    @Mock
    private TransactionMapper transactionMapper;

    private InOrder inOrder;

    @BeforeEach
    void setUp() {
        inOrder = inOrder(walletAccountService,
                transactionService,
                transactionMapper);
    }

    @Test
    void topUp() {
        // given
        TopUpRequest topUpRequest = TopUpRequest.builder()
                .amount(BigDecimal.TEN)
                .currency(Currency.TRY)
                .userId("userId")
                .build();
        WalletAccount mockWalletAccount = WalletAccount.builder()
                .status(Status.ACTIVE)
                .balance(BigDecimal.TEN)
                .currency(Currency.TRY)
                .build();
        Transaction mockTransaction = Transaction.builder().build();
        TransactionDto mockTransactionDto = TransactionDto.builder().build();

        when(walletAccountService.getWalletAccountByUserIdAndCurrency(topUpRequest.getUserId(), topUpRequest.getCurrency()))
                .thenReturn(mockWalletAccount);
        when(transactionService.save(any(Transaction.class)))
                .thenReturn(mockTransaction);
        when(transactionMapper.toTransactionDto(mockTransaction)).thenReturn(mockTransactionDto);

        // when
        TransactionDto transactionDto = topUpService.topUp(topUpRequest);

        // then
        assertThat(transactionDto).isEqualTo(mockTransactionDto);

        inOrder.verify(walletAccountService).getWalletAccountByUserIdAndCurrency(topUpRequest.getUserId(), topUpRequest.getCurrency());
        inOrder.verify(walletAccountService).save(any(WalletAccount.class));
        inOrder.verify(transactionService).save(any(Transaction.class));
        inOrder.verify(transactionMapper).toTransactionDto(mockTransaction);
        inOrder.verifyNoMoreInteractions();
    }
}