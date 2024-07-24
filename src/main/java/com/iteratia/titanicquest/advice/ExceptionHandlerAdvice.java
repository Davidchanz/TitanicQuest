package com.iteratia.titanicquest.advice;

import com.iteratia.titanicquest.dto.error.ApiError;
import com.iteratia.titanicquest.dto.error.Error;
import com.iteratia.titanicquest.exception.illegal.PassengerRecordIllegalStateException;
import com.iteratia.titanicquest.exception.illegal.StatisticsRequestIllegalStateException;
import com.iteratia.titanicquest.exception.io.ReadSourceException;
import com.iteratia.titanicquest.exception.notFound.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@RestControllerAdvice
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    static Logger logger = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);

    private void logException(String status, ApiError error, Exception ex, WebRequest request) {
        logger.error(status, error);
        logger.error("Exception", ex);
        logger.error("Request '{}'", request);
    }

    @ExceptionHandler(value = {
            StatisticsRequestIllegalStateException.class,
            PassengerRecordIllegalStateException.class,
            ReadSourceException.class // handle if load from http request source
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ApiError> handleBadRequest(
            RuntimeException ex, WebRequest request) {
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST,
                ((ServletWebRequest)request).getRequest().getRequestURI().toString(),
                new Error(ex.getClass().getName(), ex.getMessage())
        );
        logException("BAD_REQUEST '{}'", error, ex, request);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {
            EntityNotFoundException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<ApiError> handleNotFound(
            RuntimeException ex, WebRequest request) {
        ApiError error = new ApiError(HttpStatus.NOT_FOUND,
                ((ServletWebRequest)request).getRequest().getRequestURI().toString(),
                new Error(ex.getClass().getName(), ex.getMessage())
        );
        logException("NOT_FOUND '{}'", error, ex, request);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST,
                ((ServletWebRequest)request).getRequest().getRequestURI().toString(),
                new Error(ex.getClass().getName(), ex.getMessage())
        );
        logException("BAD_REQUEST Type_Mismatch '{}'", error, ex, request);
        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST,
                ((ServletWebRequest)request).getRequest().getRequestURI().toString(),
                new Error(ex.getClass().getName(), ex.getMessage())
        );
        logException("BAD_REQUEST Missing_Path_Variable'{}'", error, ex, request);
        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<Error> errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(objectError -> new Error(ex.getObjectName(), objectError.getDefaultMessage()))
                .toList();
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST,
                ((ServletWebRequest)request).getRequest().getRequestURI().toString(),
                errors.toArray(new Error[0])
        );
        logException("BAD_REQUEST Method_Argument_Not_Valid'{}'", error, ex, request);
        return handleExceptionInternal(ex, error, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST,
                ((ServletWebRequest)request).getRequest().getRequestURI().toString(),
                new Error(ex.getClass().getName(), ex.getMessage())
        );
        logException("BAD_REQUEST Message_Not_Readable'{}'", error, ex, request);
        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ApiError error = new ApiError(HttpStatus.NOT_FOUND,
                ((ServletWebRequest)request).getRequest().getRequestURI().toString(),
                new Error(ex.getClass().getName(), ex.getMessage())
        );
        logException("NOT_FOUND Handler_Not_Found'{}'", error, ex, request);
        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ApiError error = new ApiError(HttpStatus.METHOD_NOT_ALLOWED,
                ((ServletWebRequest)request).getRequest().getRequestURI().toString(),
                new Error(ex.getClass().getName(), ex.getMessage())
        );
        logException("METHOD_NOT_ALLOWED Method_Not_Supported'{}'", error, ex, request);
        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED, request);
    }

}
