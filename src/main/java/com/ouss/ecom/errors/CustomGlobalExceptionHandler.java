package com.ouss.ecom.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.ouss.ecom.errors.CustomError;
import com.ouss.ecom.errors.CustomException;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

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
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<CustomError> handleOtherExceptions(Exception ex) {
//        CustomError customError = new CustomError();
//        customError.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
//        customError.setMessage("Something went wrong, please try again later");
//        return new ResponseEntity<>(customError, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//    @ExceptionHandler(DataIntegrityViolationException.class)
//    public ResponseEntity<CustomError> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
//        CustomError customError = new CustomError();
//        customError.setStatusCode(HttpStatus.BAD_REQUEST.value());
//        customError.setMessage("Duplicate value entered, please choose another value");
//        return new ResponseEntity<>(customError, HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CustomError> handleConstraintViolationException(ConstraintViolationException ex) {
        CustomError customError = new CustomError();
        customError.setStatusCode(HttpStatus.BAD_REQUEST.value());
        customError.setMessage(ex.getConstraintViolations().iterator().next().getMessage());
        return new ResponseEntity<>(customError, HttpStatus.BAD_REQUEST);
    }
}