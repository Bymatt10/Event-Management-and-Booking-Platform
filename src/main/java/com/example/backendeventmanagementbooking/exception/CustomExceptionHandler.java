package com.example.backendeventmanagementbooking.exception;

import com.example.backendeventmanagementbooking.utils.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GenericResponse<Object>> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return new GenericResponse<>(ex.getBindingResult().getAllErrors().getFirst().getDefaultMessage(), HttpStatus.BAD_REQUEST).GenerateResponse();
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<GenericResponse<Object>> authenticationException(AuthException ex) {
        return new GenericResponse<>(ex.getMessage(), HttpStatus.UNAUTHORIZED).GenerateResponse();
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<GenericResponse<Object>> usernameNotFoundException(UsernameNotFoundException ex) {
        return new GenericResponse<>(ex.getMessage(), HttpStatus.NOT_FOUND).GenerateResponse();
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<GenericResponse<Object>> customException(CustomException ex) {
        return ex.getGenericResponse().GenerateResponse();
    }
}