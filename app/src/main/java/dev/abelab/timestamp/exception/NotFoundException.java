package dev.abelab.timestamp.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * Not found exception
 */
public class NotFoundException extends BaseException {

    /**
     * create not found exception
     *
     * @param ErrorCode error code
     */
    public NotFoundException(final ErrorCode errorCode) {
        super(NOT_FOUND, errorCode);
    }

}
