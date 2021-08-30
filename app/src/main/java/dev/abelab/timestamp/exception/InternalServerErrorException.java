package dev.abelab.timestamp.exception;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;;

/**
 * Internal server error exception
 */
public class InternalServerErrorException extends BaseException {

    /**
     * create conflict exception
     *
     * @param ErrorCode error code
     */
    public InternalServerErrorException(final ErrorCode errorCode) {
        super(INTERNAL_SERVER_ERROR, errorCode);
    }

}
