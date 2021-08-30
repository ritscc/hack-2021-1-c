package dev.abelab.timestamp.exception;

import org.springframework.http.HttpStatus;

/**
 * Base exception
 */
public class BaseException extends RuntimeException {

    /**
     * http status
     */
    private HttpStatus httpStatus;

    /**
     * error code
     */
    private ErrorCode errorCode;

    /**
     * args
     */
    private String[] args;

    /**
     * create base exception
     *
     * @param HttpStatus http status
     *
     * @param ErrorCode  error code
     */
    public BaseException(final HttpStatus httpStatus, final ErrorCode errroCode, final String... args) {
        this.httpStatus = httpStatus;
        this.errorCode = errroCode;
        this.args = args;
    }

    /**
     * getter of http status
     */
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    /**
     * getter of error code
     */
    public ErrorCode getErrorCode() {
        return this.errorCode;
    }

    /**
     * getter of args
     */
    public String[] getArgs() {
        return this.args;
    }

}
