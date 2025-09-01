package com.spring.boot.social.exceptions;

import com.spring.boot.social.dto.ExceptionDto;
import com.spring.boot.social.models.BundleMessage;
import com.spring.boot.social.services.BundleTranslationService;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandling {


    @ExceptionHandler(NotFoundResourceException.class)
    public ResponseEntity<ExceptionDto> handleNotFoundResourceException(NotFoundResourceException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ExceptionDto(
                        HttpStatus.NOT_FOUND.value(),
                        BundleTranslationService.getBundleMessageWithArAndEn(exception.getMessage()),
                        HttpStatus.NOT_FOUND.getReasonPhrase()
                )
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ExceptionDto> handleForbidden(AccessDeniedException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(
                        new ExceptionDto(
                                HttpStatus.FORBIDDEN.value(),
                                new BundleMessage(exception.getMessage()
                                ),
                                HttpStatus.FORBIDDEN.getReasonPhrase()
                        )
                );
    }

    @ExceptionHandler(
            exception = {
                    BadCredentialsException.class,
                    AccountExpiredException.class,
                    InsufficientAuthenticationException.class,
                    AuthenticationException.class,
                    UsernameNotFoundException.class,
                    CredentialsExpiredException.class,
                    DisabledException.class,
                    LockedException.class,
                    AuthenticationCredentialsNotFoundException.class,
                    InvalidCredentialsException.class,
                    ExpiredTokenException.class,
            })
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ExceptionDto> handleBadCredentialsException(Exception exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(
                        new ExceptionDto(
                                HttpStatus.UNAUTHORIZED.value(),
                                BundleTranslationService.getBundleMessageWithArAndEn(exception.getMessage()),
                                HttpStatus.UNAUTHORIZED.getReasonPhrase()
                        )
                );
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionDto> handleBadRequestException(BadRequestException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionDto(HttpStatus.BAD_REQUEST.value(), BundleTranslationService.getBundleMessageWithArAndEn(exception.getMessage()), HttpStatus.BAD_REQUEST.getReasonPhrase()));
    }

    @ExceptionHandler(exception = {
            IncorrectResultSizeDataAccessException.class,
            DataAccessException.class,
            TransactionException.class,
            TransactionSystemException.class,
    })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ExceptionDto> handleInternalServer(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), BundleTranslationService.getBundleMessageWithArAndEn(exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()));
    }

    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ExceptionDto> handleDuplicateKeyException(DuplicateKeyException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ExceptionDto(HttpStatus.CONFLICT.value(), BundleTranslationService.getBundleMessageWithArAndEn(exception.getMessage()), HttpStatus.CONFLICT.getReasonPhrase()));
    }

    @ExceptionHandler(CannotCreateTransactionException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ResponseEntity<ExceptionDto> handleDuplicateKeyException(CannotCreateTransactionException exception) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new ExceptionDto(HttpStatus.SERVICE_UNAVAILABLE.value(), BundleTranslationService.getBundleMessageWithArAndEn(exception.getMessage()), HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDto> handleValidationException(MethodArgumentNotValidException exception) {
        FieldError fieldError = exception.getBindingResult().getFieldErrors().get(0);
        String error = fieldError.getDefaultMessage();
        BundleMessage bundleMessage = BundleTranslationService.getBundleMessageWithArAndEn(error);
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionDto(HttpStatus.NOT_ACCEPTABLE.value(), bundleMessage, HttpStatus.NOT_ACCEPTABLE.getReasonPhrase()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> handleGeneralException(Exception exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ExceptionDto(
                        HttpStatus.NOT_FOUND.value(),
                        new BundleMessage(exception.getMessage()),
                        HttpStatus.NOT_FOUND.getReasonPhrase()
                )
        );
    }
}
