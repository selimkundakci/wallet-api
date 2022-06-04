package com.selimkundakcioglu.walletapi.service;

import com.selimkundakcioglu.walletapi.model.entity.Transaction;
import com.selimkundakcioglu.walletapi.model.enumtype.TransactionType;
import com.selimkundakcioglu.walletapi.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @Test
    void isPresentByReferenceCodeAndTransactionType_retunFalse() {
        // given
        String referenceCode = "referenceCode";
        TransactionType transactionType = TransactionType.CHECKOUT;

        when(transactionRepository.findByReferenceCodeAndTransactionType(referenceCode, transactionType))
                .thenReturn(Optional.empty());

        // when
        boolean response = transactionService.isPresentByReferenceCodeAndTransactionType(referenceCode, transactionType);

        // then
        assertThat(response).isFalse();
    }

    @Test
    void isPresentByReferenceCodeAndTransactionType_returnTrue() {
        // given
        String referenceCode = "referenceCode";
        TransactionType transactionType = TransactionType.CHECKOUT;

        when(transactionRepository.findByReferenceCodeAndTransactionType(referenceCode, transactionType))
                .thenReturn(Optional.of(new Transaction()));

        // when
        boolean response = transactionService.isPresentByReferenceCodeAndTransactionType(referenceCode, transactionType);

        // then
        assertThat(response).isTrue();
    }

    @Test
    void save() {
        // given
        Transaction transaction = new Transaction();
        Transaction mockTransaction = new Transaction();

        when(transactionRepository.save(transaction))
                .thenReturn(mockTransaction);

        // when
        Transaction response = transactionService.save(transaction);

        // then
        assertThat(response).isEqualTo(mockTransaction);
    }
}