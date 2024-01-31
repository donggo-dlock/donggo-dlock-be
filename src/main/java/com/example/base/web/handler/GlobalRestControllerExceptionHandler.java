package com.example.base.web.handler;


import com.example.base.common.exception.BusinessException;
import com.example.base.web.dto.ApiResponse;
import com.example.base.web.dto.ApiResponseGenerator;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.nio.file.AccessDeniedException;

import static com.example.base.common.domain.BusinessCode.*;

@Slf4j
@RestControllerAdvice
public class GlobalRestControllerExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ApiResponse<Void>> handle(BusinessException exception) {
        if (exception.isNecessaryToLog()) {
            log.error("[BusinessException] {}", exception.getMessage(), exception);
        }

        return ResponseEntity
                .status(exception.getHttpStatus())
                .body(ApiResponseGenerator.fail(exception.getErrorCode(), exception.getMessage()));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    protected ApiResponse<Void> handle(Throwable throwable) {
        log.error("[InternalServerError]{}", throwable.getMessage(), throwable);

        return ApiResponseGenerator.fail(INTERNAL_SERVER_ERROR.getCode(), INTERNAL_SERVER_ERROR.getDescription());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ConversionFailedException.class)
    protected ApiResponse<Void> handle(ConversionFailedException exception) {
        Throwable cause = exception.getCause();
        if (cause instanceof IllegalArgumentException illegalArgumentException) {
            return this.handle(illegalArgumentException);
        }

        log.error("[InternalServerError][ConversionFailed] {}", exception.getMessage(), exception);

        return ApiResponseGenerator.fail(INTERNAL_SERVER_ERROR.getCode(), INTERNAL_SERVER_ERROR.getDescription());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoResourceFoundException.class)
    protected ApiResponse<Void> handle() {
        return ApiResponseGenerator.fail(INVALID_URL.getCode(), INVALID_URL.getDescription());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    protected ApiResponse<Void> handle(AccessDeniedException exception) {
        log.info("[AccessDenied] {}", exception.getMessage());
        return ApiResponseGenerator.fail(FORBIDDEN.getCode(), FORBIDDEN.getDescription());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            IllegalArgumentException.class,
            MissingServletRequestParameterException.class,
            MethodArgumentTypeMismatchException.class,
            HttpMessageNotReadableException.class,
            HttpMediaTypeNotSupportedException.class,
            HttpMediaTypeNotAcceptableException.class,
            BindException.class
    })
    protected ApiResponse<Void> handle(Exception exception) {
        log.info("[BadRequest] {}", exception.getMessage(), exception);

        return ApiResponseGenerator.fail(BAD_REQUEST.getCode(), BAD_REQUEST.getDescription());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            MethodArgumentNotValidException.class,
    })
    protected ApiResponse<Void> handle(final MethodArgumentNotValidException exception) {
        log.info("[MethodArgumentNotValidException] {}", exception.getBindingResult().getAllErrors().get(0).getDefaultMessage());

        return ApiResponseGenerator.fail(BAD_REQUEST.getCode(), exception.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            ConstraintViolationException.class,
    })
    protected ApiResponse<Void> handle(final ConstraintViolationException exception) {
        log.info("[ConstraintViolationException] {}", exception.getMessage());

        return ApiResponseGenerator.fail(BAD_REQUEST.getCode(), exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            InvalidDataAccessApiUsageException.class
    })
    protected ApiResponse<Void> handle(final InvalidDataAccessApiUsageException exception) {
        log.info("[InvalidDataAccessApiUsageException] {}", exception.getMessage());

        return ApiResponseGenerator.fail(BAD_REQUEST.getCode(), exception.getMessage());
    }
}
