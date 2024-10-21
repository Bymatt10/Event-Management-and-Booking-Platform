package com.example.backendeventmanagementbooking.exception;

import com.example.backendeventmanagementbooking.utils.GenericResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@EqualsAndHashCode(callSuper = true)
@Data
public class CustomException extends RuntimeException {

    private GenericResponse<Object> genericResponse;

    public CustomException(GenericResponse<Object> genericResponse) {
        this.genericResponse = genericResponse;
    }

    public CustomException(HttpStatus status, String message) {
        this.genericResponse = new GenericResponse<>(message, status);
    }
    
    public ResponseEntity<GenericResponse<Object>> toResponseEntity() {
        return this.genericResponse.GenerateResponse();
    }
}
