package dev.abelab.timestamp.exception;

import static org.springframework.http.HttpStatus.CONFLICT;

/**
 * Conflict exception
 */
public class ConflictException extends BaseException {

    /**
     * create conflict exception
     *
     * @param ErrorCode error code
     */
    public ConflictException(final ErrorCode errorCode) {
        super(CONFLICT, errorCode);
    }

}
