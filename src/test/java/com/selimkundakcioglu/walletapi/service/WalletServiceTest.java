package com.selimkundakcioglu.walletapi.service;

import com.selimkundakcioglu.walletapi.constants.ExceptionCodes;
import com.selimkundakcioglu.walletapi.exception.BusinessException;
import com.selimkundakcioglu.walletapi.mapper.WalletMapper;
import com.selimkundakcioglu.walletapi.model.dto.WalletDto;
import com.selimkundakcioglu.walletapi.model.entity.Wallet;
import com.selimkundakcioglu.walletapi.model.entity.WalletAccount;
import com.selimkundakcioglu.walletapi.model.enumtype.Currency;
import com.selimkundakcioglu.walletapi.model.enumtype.Status;
import com.selimkundakcioglu.walletapi.model.request.CreateWalletAccountRequest;
import com.selimkundakcioglu.walletapi.model.request.CreateWalletRequest;
import com.selimkundakcioglu.walletapi.repository.WalletRepository;
import com.selimkundakcioglu.walletapi.stubbuilder.WalletAccountStubBuilder;
import com.selimkundakcioglu.walletapi.stubbuilder.WalletStubBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

    @InjectMocks
    private WalletService walletService;
    @Mock
    private WalletAccountService walletAccountService;
    @Mock
    private WalletRepository walletRepository;
    @Mock
    private WalletMapper walletMapper;

    private InOrder inOrder;

    @BeforeEach
    void setUp() {
        inOrder = Mockito.inOrder(walletAccountService,
                walletRepository,
                walletMapper);
    }

    @Test
    void createWallet() {
        // given
        CreateWalletRequest createWalletRequest = WalletStubBuilder.getCreateWalletRequest();
        WalletAccount mockWalletAccount = new WalletAccount();
        Wallet mockWallet = new Wallet();
        WalletDto mockWalletDto = WalletDto.builder().build();

        when(walletRepository.findByUserIdAndStatus(createWalletRequest.getUserId(), Status.ACTIVE)).thenReturn(Optional.empty());
        when(walletAccountService.createWalletAccount(createWalletRequest.getCurrency())).thenReturn(mockWalletAccount);
        when(walletRepository.save(any(Wallet.class))).thenReturn(mockWallet);
        when(walletMapper.toWalletDto(mockWallet)).thenReturn(mockWalletDto);

        // when
        WalletDto walletDto = walletService.createWallet(createWalletRequest);

        // then
        assertThat(walletDto).isEqualTo(mockWalletDto);
    }

    @Test
    void createWallet_throwsException_when_walletAlreadyExistWithGivenUserId() {
        // given
        CreateWalletRequest createWalletRequest = WalletStubBuilder.getCreateWalletRequest();
        Wallet mockWallet = new Wallet();

        when(walletRepository.findByUserIdAndStatus(createWalletRequest.getUserId(), Status.ACTIVE)).thenReturn(Optional.of(mockWallet));

        // when
        Throwable throwable = catchThrowable(() -> walletService.createWallet(createWalletRequest));

        // then
        assertThat(throwable).isNotNull().isExactlyInstanceOf(BusinessException.class);
        assertThat(((BusinessException) throwable).getCode()).isEqualTo(ExceptionCodes.ALREADY_AN_ACTIVE_WALLET_FOUND);

        inOrder.verify(walletRepository).findByUserIdAndStatus(createWalletRequest.getUserId(), Status.ACTIVE);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void createWalletAccount() {
        // given
        CreateWalletAccountRequest createWalletAccountRequest = WalletAccountStubBuilder.getCreateWalletAccountRequest(Currency.EUR);
        Wallet mockWallet = WalletStubBuilder.getWallet();
        WalletAccount newWalletAccount = WalletAccountStubBuilder.getWalletAccount(createWalletAccountRequest.getCurrency());
        newWalletAccount.setWallet(mockWallet);
        WalletDto mockWalletDto = WalletDto.builder().build();

        when(walletRepository.findByUserIdAndStatus(createWalletAccountRequest.getUserId(), Status.ACTIVE))
                .thenReturn(Optional.of(mockWallet));
        when(walletAccountService.createAndSaveWalletAccount(createWalletAccountRequest.getCurrency(), mockWallet))
                .thenReturn(newWalletAccount);
        when(walletMapper.toWalletDto(any(Wallet.class))).thenReturn(mockWalletDto);

        // when
        WalletDto walletDto = walletService.createWalletAccount(createWalletAccountRequest);

        // then
        assertThat(walletDto).isEqualTo(mockWalletDto);

        inOrder.verify(walletRepository).findByUserIdAndStatus(createWalletAccountRequest.getUserId(), Status.ACTIVE);
        inOrder.verify(walletAccountService).createAndSaveWalletAccount(createWalletAccountRequest.getCurrency(), mockWallet);
        inOrder.verify(walletRepository).save(any(Wallet.class));
        inOrder.verify(walletMapper).toWalletDto(mockWallet);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void createWalletAccount_throwsException_when_walletNotFound() {
        // given
        CreateWalletAccountRequest createWalletAccountRequest = WalletAccountStubBuilder.getCreateWalletAccountRequest(Currency.EUR);

        when(walletRepository.findByUserIdAndStatus(createWalletAccountRequest.getUserId(), Status.ACTIVE))
                .thenReturn(Optional.empty());

        // when
        Throwable throwable = catchThrowable(() -> walletService.createWalletAccount(createWalletAccountRequest));

        // then
        assertThat(throwable).isNotNull().isExactlyInstanceOf(BusinessException.class);
        assertThat(((BusinessException) throwable).getCode()).isEqualTo(ExceptionCodes.WALLET_NOT_FOUND);

        inOrder.verify(walletRepository).findByUserIdAndStatus(createWalletAccountRequest.getUserId(), Status.ACTIVE);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void createWalletAccount_throwsException_when_accountIsPresentWithGivenCurrency() {
        // given
        CreateWalletAccountRequest createWalletAccountRequest = WalletAccountStubBuilder.getCreateWalletAccountRequest(Currency.TRY);
        Wallet mockWallet = WalletStubBuilder.getWallet();

        when(walletRepository.findByUserIdAndStatus(createWalletAccountRequest.getUserId(), Status.ACTIVE))
                .thenReturn(Optional.of(mockWallet));

        // when
        Throwable throwable = catchThrowable(() -> walletService.createWalletAccount(createWalletAccountRequest));

        // then
        assertThat(throwable).isNotNull().isExactlyInstanceOf(BusinessException.class);
        assertThat(((BusinessException) throwable).getCode()).isEqualTo(ExceptionCodes.ACCOUNT_PRESENT_WITH_GIVEN_CURRENCY);

        inOrder.verify(walletRepository).findByUserIdAndStatus(createWalletAccountRequest.getUserId(), Status.ACTIVE);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void queryWallet() {
        // given
        String userId = "userId";
        Wallet mockWallet = WalletStubBuilder.getWallet();
        WalletDto mockWalletDto = WalletDto.builder().build();

        when(walletRepository.findByUserIdAndStatus(userId, Status.ACTIVE)).thenReturn(Optional.of(mockWallet));
        when(walletMapper.toWalletDto(mockWallet)).thenReturn(mockWalletDto);

        // when
        WalletDto walletDto = walletService.queryWallet(userId);

        // then
        assertThat(walletDto).isEqualTo(mockWalletDto);
    }
}