package com.selimkundakcioglu.walletapi.service;

import com.selimkundakcioglu.walletapi.constants.ExceptionCodes;
import com.selimkundakcioglu.walletapi.exception.BusinessException;
import com.selimkundakcioglu.walletapi.mapper.TransactionMapper;
import com.selimkundakcioglu.walletapi.model.dto.TransactionDto;
import com.selimkundakcioglu.walletapi.model.entity.Transaction;
import com.selimkundakcioglu.walletapi.model.entity.WalletAccount;
import com.selimkundakcioglu.walletapi.model.enumtype.Status;
import com.selimkundakcioglu.walletapi.model.enumtype.TransactionType;
import com.selimkundakcioglu.walletapi.model.request.CheckoutRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CheckoutService {

    private final WalletAccountService walletAccountService;
    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    @Transactional
    public TransactionDto checkout(CheckoutRequest checkoutRequest) {
        WalletAccount walletAccount = walletAccountService.getWalletAccountByUserIdAndCurrency(checkoutRequest.getUserId(), checkoutRequest.getCurrency());

        checkTransactionReferenceCode(checkoutRequest);
        checkBalance(checkoutRequest.getAmount(), walletAccount);

        Transaction savedTransaction = createAndSaveTransaction(checkoutRequest, walletAccount);
        updateBalance(checkoutRequest, walletAccount);
        return transactionMapper.toTransactionDto(savedTransaction);
    }

    private Transaction createAndSaveTransaction(CheckoutRequest checkoutRequest, WalletAccount walletAccount) {
        Transaction transaction = Transaction.builder()
                .transactionType(TransactionType.CHECKOUT)
                .amount(checkoutRequest.getAmount())
                .referenceCode(checkoutRequest.getReferenceId())
                .walletAccount(walletAccount)
                .status(Status.ACTIVE)
                .build();
        return transactionService.save(transaction);
    }

    private void updateBalance(CheckoutRequest checkoutRequest, WalletAccount walletAccount) {
        walletAccount.setBalance(walletAccount.getBalance().subtract(checkoutRequest.getAmount()));
        walletAccountService.save(walletAccount);
    }

    private void checkBalance(BigDecimal amount, WalletAccount walletAccount) {
        if (walletAccount.getBalance().compareTo(amount) < 0) {
            throw new BusinessException(ExceptionCodes.CHECKOUT_BALANCE_NOT_ENOUGH);
        }
    }

    private void checkTransactionReferenceCode(CheckoutRequest checkoutRequest) {
        if (transactionService.isPresentByReferenceCodeAndTransactionType(checkoutRequest.getReferenceId(), TransactionType.CHECKOUT)) {
            throw new BusinessException(ExceptionCodes.TRANSACTION_FOUND_WITH_SAME_REFERENCE_CODE);
        }
    }
}
