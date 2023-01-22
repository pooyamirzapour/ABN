package com.abn.recipe.common.exception;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.abn.recipe.common.config.MetricConstant.ABN_EXCEPTION;
import static com.abn.recipe.common.exception.ErrorCode.DATABASE_EXCEPTION;
import static com.abn.recipe.common.exception.ErrorCode.UNKNOWN_ERROR;

/**
 * Exception Handler class for creating proper message and log.
 */

@ControllerAdvice
@Slf4j
@AllArgsConstructor
public class ExceptionHandlers {

    private final MeterRegistry meterRegistry;

    /**
     * Handle all exceptions with message, status, and log.
     *
     * @param exception - ABNServiceException
     * @return ResponseEntity
     */
    @ExceptionHandler(ABNServiceException.class)
    public ResponseEntity<?> ABNServiceException(ABNServiceException exception) {

        meterRegistry.counter(ABN_EXCEPTION,
                Tags.of("ErrorCode", exception.getErrorCode().name())).increment();

        log.error(exception.getMessage());

        return new ResponseEntity<>(new ErrorMsg(exception.getErrorCode().getValue(), exception.getMessage()),
                exception.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> ABNServiceException(Exception exception) {

        log.error(exception.getMessage());

        meterRegistry.counter(ABN_EXCEPTION, Tags.of("ErrorCode", UNKNOWN_ERROR.name())).increment();

        return new ResponseEntity<>(new ErrorMsg(2000, exception.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> dataIntegrityViolationException(DataIntegrityViolationException exception) {

        log.error(exception.getMessage());

        meterRegistry.counter(ABN_EXCEPTION, Tags.of("ErrorCode", DATABASE_EXCEPTION.name())).increment();

        return new ResponseEntity<>(new ErrorMsg(DATABASE_EXCEPTION.getValue(), DATABASE_EXCEPTION.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }


}
