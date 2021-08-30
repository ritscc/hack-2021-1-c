package dev.abelab.timestamp.api.controller;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import dev.abelab.timestamp.exception.ErrorCode;
import dev.abelab.timestamp.exception.BaseException;
import dev.abelab.timestamp.api.response.ErrorResponse;

/**
 * Rest controller exception advice
 */
@Slf4j
@Controller
@RestControllerAdvice
@RequiredArgsConstructor
public class RestControllerExceptionAdvice extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    /**
     * エラーメッセージを取得
     *
     * @param exception exception
     *
     * @return エラーメッセージ
     */
    private String getErrorMessage(final BaseException exception) {
        final var messageKey = exception.getErrorCode().getMessageKey();
        final var args = exception.getArgs();
        return this.messageSource.getMessage(messageKey, args, Locale.ENGLISH);
    }

    /**
     * Handle not found exception
     *
     * @param exception exception
     *
     * @return response entity
     */
    @RequestMapping("/api/**")
    public ResponseEntity<ErrorResponse> handleApiNotFoundException() {
        final var errorCode = ErrorCode.NOT_FOUND_API;
        final var message = messageSource.getMessage(errorCode.getMessageKey(), null, Locale.ENGLISH);
        final var errorResponse = ErrorResponse.builder().message(message).code(errorCode.getCode()).build();

        log.warn(message);

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handle unexpected exception
     *
     * @param exception exception
     *
     * @return response entity
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(final Exception exception) {
        final var errorCode = ErrorCode.UNEXPECTED_ERROR;
        final var message = messageSource.getMessage(errorCode.getMessageKey(), null, Locale.ENGLISH);
        final var errorResponse = ErrorResponse.builder().message(message).code(errorCode.getCode()).build();

        log.error(exception.getMessage(), exception);

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle base exception
     *
     * @param exception exception
     *
     * @return response entity
     */
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(final BaseException exception) {
        final var message = this.getErrorMessage(exception);
        final var errorCode = exception.getErrorCode();
        final var errorResponse = ErrorResponse.builder().message(message).code(errorCode.getCode()).build();

        if (exception.getHttpStatus().is4xxClientError()) {
            log.warn(String.format("%d: %s", errorCode.getCode(), message));
        } else if (exception.getHttpStatus().is5xxServerError()) {
            log.error(String.format("%d: %s", errorCode.getCode(), message));
        }

        return new ResponseEntity<>(errorResponse, exception.getHttpStatus());
    }

}
