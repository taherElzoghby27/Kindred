package com.spring.boot.social.exceptions;

import com.spring.boot.social.dto.ExceptionDto;
import com.spring.boot.social.entity.BundleMessage;
import com.spring.boot.social.services.BundleTranslationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Objects;

@ControllerAdvice
@Slf4j
public class ExceptionHandling {


    @ExceptionHandler(NotFoundResourceException.class)
    public ResponseEntity<ExceptionDto> handleNotFoundResource(NotFoundResourceException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ExceptionDto(
                        HttpStatus.NOT_FOUND.value(),
                        BundleTranslationService.getBundleMessageWithArAndEn(exception.getMessage()),
                        HttpStatus.NOT_FOUND.getReasonPhrase()
                )
        );
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ExceptionDto> handleNoResourceFound(NoResourceFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ExceptionDto(
                        HttpStatus.NOT_FOUND.value(),
                        new BundleMessage(exception.getMessage()),
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
                                new BundleMessage(exception.getMessage()),
                                HttpStatus.FORBIDDEN.getReasonPhrase()
                        )
                );
    }

    @ExceptionHandler({
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
        BundleMessage bundleMessage = BundleTranslationService.getBundleMessageWithArAndEn(exception.getMessage());
        if (Objects.isNull(bundleMessage.getMessageAr())) {
            bundleMessage = new BundleMessage(exception.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(
                        new ExceptionDto(
                                HttpStatus.UNAUTHORIZED.value(),
                                bundleMessage,
                                HttpStatus.UNAUTHORIZED.getReasonPhrase()
                        )
                );
    }

    @ExceptionHandler({
            BadRequestException.class,
            IncorrectResultSizeDataAccessException.class
    })
    public ResponseEntity<ExceptionDto> handleBadRequestException(BadRequestException exception) {
        BundleMessage bundleMessage = BundleTranslationService.getBundleMessageWithArAndEn(exception.getMessage());
        if (Objects.isNull(bundleMessage.getMessageAr())) {
            bundleMessage = new BundleMessage(exception.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ExceptionDto(HttpStatus.BAD_REQUEST.value(),
                        bundleMessage,
                        HttpStatus.BAD_REQUEST.getReasonPhrase()
                )
        );
    }

    @ExceptionHandler({
            DataAccessException.class,
            TransactionException.class,
            TransactionSystemException.class,
    })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ExceptionDto> handleInternalServer(Exception exception) {
        BundleMessage bundleMessage = BundleTranslationService.getBundleMessageWithArAndEn(exception.getMessage());
        if (Objects.isNull(bundleMessage.getMessageAr())) {
            bundleMessage = new BundleMessage(exception.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ExceptionDto(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        bundleMessage,
                        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()
                )
        );
    }

    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ExceptionDto> handleDuplicateKeyException(DuplicateKeyException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ExceptionDto(HttpStatus.CONFLICT.value(),
                        BundleTranslationService.getBundleMessageWithArAndEn(exception.getMessage()),
                        HttpStatus.CONFLICT.getReasonPhrase()
                )
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDto> handleValidationException(MethodArgumentNotValidException exception) {
        FieldError fieldError = exception.getBindingResult().getFieldErrors().get(0);
        String error = fieldError.getDefaultMessage();
        BundleMessage bundleMessage = BundleTranslationService.getBundleMessageWithArAndEn(error);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ExceptionDto(
                        HttpStatus.BAD_REQUEST.value(),
                        bundleMessage,
                        HttpStatus.BAD_REQUEST.getReasonPhrase()
                )
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> handleGeneralException(Exception exception) {
        log.error("Unexpected error : ", exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ExceptionDto(
                        500,
                        new BundleMessage("Internal server error"),
                        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()
                )
        );
    }
}
