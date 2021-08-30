package dev.abelab.timestamp.exception;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

/**
 * Unauthorized exception
 */
public class UnauthorizedException extends BaseException {

    /**
     * create unauthorized exception
     *
     * @param ErrorCode error code
     */
    public UnauthorizedException(final ErrorCode errorCode) {
        super(UNAUTHORIZED, errorCode);
    }

}
