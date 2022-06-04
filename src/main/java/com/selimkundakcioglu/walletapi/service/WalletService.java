package com.selimkundakcioglu.walletapi.service;

import com.selimkundakcioglu.walletapi.constants.ExceptionCodes;
import com.selimkundakcioglu.walletapi.exception.BusinessException;
import com.selimkundakcioglu.walletapi.mapper.WalletMapper;
import com.selimkundakcioglu.walletapi.model.dto.WalletDto;
import com.selimkundakcioglu.walletapi.model.entity.Wallet;
import com.selimkundakcioglu.walletapi.model.entity.WalletAccount;
import com.selimkundakcioglu.walletapi.model.enumtype.Status;
import com.selimkundakcioglu.walletapi.model.request.CreateWalletAccountRequest;
import com.selimkundakcioglu.walletapi.model.request.CreateWalletRequest;
import com.selimkundakcioglu.walletapi.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletAccountService walletAccountService;
    private final WalletRepository walletRepository;
    private final WalletMapper walletMapper;

    public WalletDto createWallet(CreateWalletRequest createWalletRequest) {
        checkIfThereIsAnActiveUser(createWalletRequest.getUserId());

        WalletAccount walletAccount = walletAccountService.createWalletAccount(createWalletRequest.getCurrency());
        Wallet wallet = Wallet.builder()
                .walletAccounts(Set.of(walletAccount))
                .status(Status.ACTIVE)
                .userId(createWalletRequest.getUserId())
                .build();
        walletAccount.setWallet(wallet);

        Wallet savedWallet = walletRepository.save(wallet);
        return walletMapper.toWalletDto(savedWallet);
    }

    public WalletDto createWalletAccount(CreateWalletAccountRequest createWalletAccountRequest) {
        Wallet wallet = walletRepository.findByUserIdAndStatus(createWalletAccountRequest.getUserId(), Status.ACTIVE)
                .orElseThrow(() -> new BusinessException(ExceptionCodes.WALLET_NOT_FOUND));

        checkIfAnAccountIsPresentWithGivenCurrency(createWalletAccountRequest, wallet);

        WalletAccount walletAccount = walletAccountService.createAndSaveWalletAccount(createWalletAccountRequest.getCurrency(), wallet);

        wallet.getWalletAccounts().add(walletAccount);
        walletRepository.save(wallet);
        return walletMapper.toWalletDto(walletAccount.getWallet());
    }

    public WalletDto queryWallet(String userId) {
        Optional<Wallet> optionalWallet = walletRepository.findByUserIdAndStatus(userId, Status.ACTIVE);

        return optionalWallet.map(walletMapper::toWalletDto).orElse(null);
    }

    private void checkIfAnAccountIsPresentWithGivenCurrency(CreateWalletAccountRequest createWalletAccountRequest, Wallet wallet) {
        boolean accountIsPresentWithGivenCurrency = !wallet.getWalletAccounts()
                .stream()
                .filter(walletAccount -> walletAccount.getCurrency().equals(createWalletAccountRequest.getCurrency()))
                .toList()
                .isEmpty();
        if (accountIsPresentWithGivenCurrency) {
            throw new BusinessException(ExceptionCodes.ACCOUNT_PRESENT_WITH_GIVEN_CURRENCY);
        }
    }

    private void checkIfThereIsAnActiveUser(String userId) {
        Optional<Wallet> optionalWallet = walletRepository.findByUserIdAndStatus(userId, Status.ACTIVE);
        if (optionalWallet.isPresent()) {
            throw new BusinessException(ExceptionCodes.ALREADY_AN_ACTIVE_WALLET_FOUND);
        }
    }
}
