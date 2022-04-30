package com.jarasoft.handler;

import com.jarasoft.dto.error.ErrorDetails;
import com.jarasoft.dto.error.ValidationError;
import com.jarasoft.exception.ResourceNotFoundException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private final MessageSource messageSource;

    public RestExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handlerResourceNotFoundException(ResourceNotFoundException rnfe, HttpServletRequest request) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setTimestamp(new Date().getTime());
        errorDetails.setStatus(HttpStatus.NOT_FOUND.value());
        errorDetails.setTitle("Resource not found");
        errorDetails.setDetail(rnfe.getMessage());
        errorDetails.setDeveloperMessage(rnfe.getClass().getName());
        return new ResponseEntity<>(errorDetails, null, HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException manve,HttpHeaders headers,HttpStatus status, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setTimestamp(new Date().getTime());
        errorDetails.setStatus(HttpStatus.BAD_REQUEST.value());
        errorDetails.setTitle("Validation failed");
        errorDetails.setDetail("Input validation failed");
        errorDetails.setDeveloperMessage(manve.getClass().getName());
        //Create validationError instances
        List<FieldError> fieldErrors = manve.getBindingResult().
                getFieldErrors();
        for (FieldError fe : fieldErrors) {
            List<ValidationError> validationErrorList = errorDetails.getErrors().computeIfAbsent
                    (fe.getField(), k -> new ArrayList<>());
            ValidationError validationError = new ValidationError();
            validationError.setCode(fe.getCode());
            validationError.setMessage(messageSource.getMessage(fe, null));
            validationErrorList.add(validationError);
        }
        return handleExceptionInternal(manve, errorDetails, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setTimestamp(new Date().getTime());
        errorDetails.setStatus(status.value());
        errorDetails.setTitle("Message Not Readable");
        errorDetails.setDetail(ex.getMessage());
        errorDetails.setDeveloperMessage(ex.getClass().getName());
        return handleExceptionInternal(ex, errorDetails, headers, status, request);
    }
}
