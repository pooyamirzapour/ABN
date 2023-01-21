package com.abn.recipe.common.exception;

import org.springframework.http.HttpStatus;

/**
 * A customized exception.
 *
 * @author Pooya Mirzapour (pooyamirzapour@gmail.com)
 */

public class ABNServiceException extends RuntimeException {

    private final ErrorCode errorCode;
    private final HttpStatus httpStatus;

    /**
     * Build a new exception with the specified error code, detail message,status, and username for log.
     *
     * @param message    - Message
     * @param errorCode  - Error Code
     * @param httpStatus - Http Status
     */
    public ABNServiceException(String message, ErrorCode errorCode, HttpStatus httpStatus) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }



}
