package com.selimkundakcioglu.walletapi.service;

import com.selimkundakcioglu.walletapi.mapper.TransactionMapper;
import com.selimkundakcioglu.walletapi.model.dto.TransactionDto;
import com.selimkundakcioglu.walletapi.model.entity.Transaction;
import com.selimkundakcioglu.walletapi.model.entity.WalletAccount;
import com.selimkundakcioglu.walletapi.model.enumtype.Status;
import com.selimkundakcioglu.walletapi.model.enumtype.TransactionType;
import com.selimkundakcioglu.walletapi.model.request.TopUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TopUpService {

    private final WalletAccountService walletAccountService;
    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    public TransactionDto topUp(TopUpRequest topUpRequest) {
        WalletAccount walletAccount = walletAccountService.getWalletAccountByUserIdAndCurrency(topUpRequest.getUserId(), topUpRequest.getCurrency());
        walletAccount.setBalance(walletAccount.getBalance().add(topUpRequest.getAmount()));
        walletAccountService.save(walletAccount);

        Transaction topUpTransaction = Transaction.builder()
                .status(Status.ACTIVE)
                .amount(topUpRequest.getAmount())
                .transactionType(TransactionType.TOP_UP)
                .walletAccount(walletAccount)
                .build();

        Transaction savedTransaction = transactionService.save(topUpTransaction);
        return transactionMapper.toTransactionDto(savedTransaction);
    }
}
