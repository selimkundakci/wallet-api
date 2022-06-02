package com.selimkundakcioglu.walletapi.controller;

import com.selimkundakcioglu.walletapi.lock.LockableController;
import com.selimkundakcioglu.walletapi.lock.LockableService;
import com.selimkundakcioglu.walletapi.model.dto.TransactionDto;
import com.selimkundakcioglu.walletapi.model.request.CheckoutRequest;
import com.selimkundakcioglu.walletapi.model.response.Response;
import com.selimkundakcioglu.walletapi.model.response.ResponseFactory;
import com.selimkundakcioglu.walletapi.service.CheckoutService;
import com.hazelcast.cp.lock.FencedLock;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/checkout")
public class CheckoutController extends LockableController {

    private static final String CHECKOUT = "checkout";

    private final ResponseFactory responseFactory;
    private final CheckoutService checkoutService;

    protected CheckoutController(CheckoutService checkoutService, LockableService lockableService, ResponseFactory responseFactory) {
        super(lockableService);
        this.checkoutService = checkoutService;
        this.responseFactory = responseFactory;
    }

    @PostMapping
    public Response<TransactionDto> checkout(@RequestBody CheckoutRequest checkoutRequest) {
        FencedLock lock = tryLock(checkoutRequest.getUserId(), CHECKOUT);
        try {
            TransactionDto transactionDto = checkoutService.checkout(checkoutRequest);
            return responseFactory.response(transactionDto);
        } finally {
            unlock(lock, CHECKOUT, checkoutRequest.getUserId());
        }
    }
}
