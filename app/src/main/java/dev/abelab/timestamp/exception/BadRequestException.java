package dev.abelab.timestamp.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * Bad Request exception
 */
public class BadRequestException extends BaseException {

    /**
     * create bad request exception
     *
     * @param ErrorCode error code
     */
    public BadRequestException(final ErrorCode errorCode) {
        super(BAD_REQUEST, errorCode);
    }

}
