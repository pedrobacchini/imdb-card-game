package com.pedrobacchini.imdbcardgame.adapter.input.web.v1.exception;

import com.pedrobacchini.imdbcardgame.application.exception.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
        HttpMessageNotReadableException ex,
        HttpHeaders headers,
        HttpStatus status,
        WebRequest request) {
        String shortMessage = "Invalid message";
        String stacktrace = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
        List<ApiError> apiErrors = Collections.singletonList(new ApiError(shortMessage, stacktrace));
        return handleExceptionInternal(ex, apiErrors, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex,
        HttpHeaders headers,
        HttpStatus status,
        WebRequest request) {
        List<ApiError> apiErrors = listOfErrors(ex.getBindingResult());
        return handleExceptionInternal(ex, apiErrors, headers, HttpStatus.BAD_REQUEST, request);
    }


    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request) {
        String shortMessage = "Resource not found";
        String stacktrace = ex.toString();
        List<ApiError> apiErrors = Collections.singletonList(new ApiError(shortMessage, stacktrace));
        return handleExceptionInternal(ex, apiErrors, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    private List<ApiError> listOfErrors(BindingResult bindingResult) {
        List<ApiError> apiErrors = new ArrayList<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String shortMessage = fieldError.getDefaultMessage();
            String stacktrace = fieldError.toString();
            apiErrors.add(new ApiError(shortMessage, stacktrace));
        }
        return apiErrors;
    }

}
