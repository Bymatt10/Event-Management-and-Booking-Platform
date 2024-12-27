package com.example.backendeventmanagementbooking.exception;

import com.example.backendeventmanagementbooking.utils.GenericResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.http.exceptions.HttpException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GenericResponse<Object>> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return new GenericResponse<>(ex.getBindingResult().getAllErrors().getFirst().getDefaultMessage(),
                HttpStatus.BAD_REQUEST).GenerateResponse();
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
        log.error("Error: ", ex);
        return ex.getGenericResponse().GenerateResponse();
    }

    @ExceptionHandler(HttpException.class)
    public ResponseEntity<GenericResponse<Object>> paypalException(HttpException ex)
            throws JsonMappingException, JsonProcessingException {
        log.error("Error: ", ex);
        return new GenericResponse<>(HttpStatus.BAD_REQUEST,
                new ObjectMapper().readValue(ex.getMessage(), Object.class))
            .GenerateResponse();
    }
}