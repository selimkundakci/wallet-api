package com.selimkundakcioglu.walletapi.exceptionhandler;

import com.selimkundakcioglu.walletapi.exception.BaseException;
import com.selimkundakcioglu.walletapi.exception.BusinessException;
import com.selimkundakcioglu.walletapi.exception.LockException;
import com.selimkundakcioglu.walletapi.model.response.Response;
import com.selimkundakcioglu.walletapi.model.response.ResponseFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestExceptionHandlerTest {

    private RestExceptionHandler handler;
    private InOrder inOrder;

    @Mock
    private ResponseFactory responseFactory;

    @Mock
    private MessageSource messageSource;

    @Mock
    private ExceptionHttpStatusResolver exceptionHttpStatusResolver;

    @BeforeEach
    void setUp() {
        handler = new RestExceptionHandler(responseFactory, messageSource, exceptionHttpStatusResolver);
        inOrder = inOrder(responseFactory, messageSource, exceptionHttpStatusResolver);
    }

    @Test
    void handleAppExceptions_givenAppException_returnsErrorResponse() {
        // given
        String error = "error";
        String message = "message";
        BaseException exception = new BusinessException(error);
        Response mockResponse = new Response<>(null, error, message);

        when(messageSource.getMessage(eq(error), any(), any())).thenReturn(message);
        when(responseFactory.response(error, message)).thenReturn(mockResponse);

        // when
        Response<?> response = handler.handleAppExceptions(exception);

        // then
        assertThat(response).isEqualTo(mockResponse);

        inOrder.verify(messageSource).getMessage(eq(error), any(), any());
        inOrder.verify(responseFactory).response(error, message);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void handleBusinessExceptions_givenAppException_returnsErrorResponse() {
        // given
        String error = "error";
        String message = "message";
        BusinessException exception = new BusinessException(error);
        Response mockResponse = new Response<>(null, error, message);

        when(messageSource.getMessage(eq(error), any(), any())).thenReturn(message);
        when(responseFactory.response(error, message)).thenReturn(mockResponse);

        // when
        Response<?> response = handler.handleBusinessException(exception);

        // then
        assertThat(response).isEqualTo(mockResponse);

        inOrder.verify(messageSource).getMessage(eq(error), any(), any());
        inOrder.verify(responseFactory).response(error, message);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void handleLockExceptions_returnsErrorResponse() {
        // given
        String error = "error";
        String message = "message";
        LockException exception = new LockException(error);
        Response mockResponse = new Response<>(null, error, message);

        when(messageSource.getMessage(eq(error), any(), any())).thenReturn(message);
        when(responseFactory.response(error, message)).thenReturn(mockResponse);

        // when
        Response<?> response = handler.handleLockException(exception);

        // then
        assertThat(response).isEqualTo(mockResponse);

        inOrder.verify(messageSource).getMessage(eq(error), any(), any());
        inOrder.verify(responseFactory).response(error, message);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void handleException_givenException_returnsErrorResponse() {
        // given
        Exception exception = new RuntimeException();
        HttpServletResponse httpResponse = new MockHttpServletResponse();
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        String error = String.valueOf(httpStatus.value());
        String message = httpStatus.getReasonPhrase();
        Response mockResponse = new Response<>(null, error, message);

        when(exceptionHttpStatusResolver.resolve(exception)).thenReturn(httpStatus);
        when(responseFactory.response(error, message)).thenReturn(mockResponse);

        // when
        Response<?> response = handler.handleException(exception, httpResponse);

        // then
        assertThat(response).isEqualTo(mockResponse);

        inOrder.verify(exceptionHttpStatusResolver).resolve(exception);
        inOrder.verify(responseFactory).response(error, message);
        inOrder.verifyNoMoreInteractions();
    }
}