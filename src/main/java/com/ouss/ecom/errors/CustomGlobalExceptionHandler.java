package com.ouss.ecom.errors;

import jakarta.validation.ConstraintViolationException;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.ouss.ecom.errors.CustomError;
import com.ouss.ecom.errors.CustomException;

import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        Map<String,String>errorMap=new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error->
        {
            errorMap.put(error.getField(),error.getDefaultMessage());
        });
        CustomError customError = new CustomError();
        customError.setStatusCode(HttpStatus.BAD_REQUEST.value());
        customError.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(CustomException.NotFoundException.class)
    public ResponseEntity<CustomError> handleNotFoundException(CustomException.NotFoundException ex) {
        CustomError customError = new CustomError();
        customError.setStatusCode(HttpStatus.NOT_FOUND.value());
        customError.setMessage(ex.getMessage());
        return new ResponseEntity<>(customError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomException.BadRequestException.class)
    public ResponseEntity<CustomError> handleBadRequestException(CustomException.BadRequestException ex) {
        CustomError customError = new CustomError();
        customError.setStatusCode(HttpStatus.BAD_REQUEST.value());
        customError.setMessage(ex.getMessage());
        return new ResponseEntity<>(customError, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(CustomException.UnauthenticatedException.class)
    public ResponseEntity<CustomError> handleUnauthenticatedException(CustomException.UnauthenticatedException ex) {
        CustomError customError = new CustomError();
        customError.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        customError.setMessage(ex.getMessage());
        return new ResponseEntity<>(customError, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(CustomException.UnauthorizedException.class)
    public ResponseEntity<CustomError> handleUnauthorizedException(CustomException.UnauthorizedException ex) {
        CustomError customError = new CustomError();
        customError.setStatusCode(HttpStatus.FORBIDDEN.value());
        customError.setMessage(ex.getMessage());
        return new ResponseEntity<>(customError, HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<CustomError> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        CustomError customError = new CustomError();
//        customError.setStatusCode(HttpStatus.BAD_REQUEST.value());
        customError.setMessage("Duplicate value entered, please choose another value");
        customError.setMessage(ex.getMessage());
        return new ResponseEntity<>(customError, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CustomError> accessDeniedException(AccessDeniedException ex) {
        CustomError customError = new CustomError();
        customError.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        customError.setMessage(ex.getMessage());
        return new ResponseEntity<>(customError, HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomError> handleOtherExceptions(Exception ex) {
        System.out.println(ex.toString());
        CustomError customError = new CustomError();
        customError.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        customError.setMessage(ex.getMessage());
        return new ResponseEntity<>(customError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CustomError> handleConstraintViolationException(ConstraintViolationException ex) {
        CustomError customError = new CustomError();
        customError.setStatusCode(HttpStatus.BAD_REQUEST.value());
        customError.setMessage(ex.getConstraintViolations().iterator().next().getMessage());
        return new ResponseEntity<>(customError, HttpStatus.BAD_REQUEST);
    }
}