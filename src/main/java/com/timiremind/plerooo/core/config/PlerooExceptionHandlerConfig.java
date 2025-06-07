package com.timiremind.plerooo.core.config;

import static com.timiremind.plerooo.core.util.DateUtil.getErrorTimeStamp;

import com.timiremind.plerooo.core.exception.RegistrationException;
import com.timiremind.plerooo.core.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PlerooExceptionHandlerConfig {
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(
            UsernameNotFoundException exception, HttpServletRequest servletRequest) {
        return new ResponseEntity<>(
                new ErrorResponse(
                        getErrorTimeStamp(System.currentTimeMillis()),
                        HttpStatus.NOT_FOUND.value(),
                        HttpStatus.NOT_FOUND.getReasonPhrase(),
                        exception.getMessage(),
                        servletRequest.getRequestURI()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RegistrationException.class)
    public ResponseEntity<ErrorResponse> handleUsernameAlreadyExists(
            RegistrationException exception, HttpServletRequest servletRequest) {
        return new ResponseEntity<>(
                new ErrorResponse(
                        getErrorTimeStamp(System.currentTimeMillis()),
                        HttpStatus.CONFLICT.value(),
                        HttpStatus.CONFLICT.getReasonPhrase(),
                        exception.getMessage(),
                        servletRequest.getRequestURI()),
                HttpStatus.CONFLICT);
    }
}
