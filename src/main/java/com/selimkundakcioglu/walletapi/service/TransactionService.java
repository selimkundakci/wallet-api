package com.selimkundakcioglu.walletapi.service;

import com.selimkundakcioglu.walletapi.model.entity.Transaction;
import com.selimkundakcioglu.walletapi.model.enumtype.TransactionType;
import com.selimkundakcioglu.walletapi.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public boolean isPresentByReferenceCodeAndTransactionType(String referenceCode, TransactionType transactionType) {
        return transactionRepository.findByReferenceCodeAndTransactionType(referenceCode, transactionType).isPresent();
    }

    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }
}
