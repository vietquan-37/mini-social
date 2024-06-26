package com.example.social_web.advice;

import com.example.social_web.exception.EmailExistedException;
import com.example.social_web.exception.UserMistake;
import com.example.social_web.payload.response.APIResponse;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(APIResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(errors)
                .build());
    }
    @ExceptionHandler(LockedException.class)
    public ResponseEntity<APIResponse> handleLockedException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(APIResponse.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .error("User account is locked")
                .build());
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<APIResponse> handleNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(APIResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .error(ex.getMessage())
                .build());
    }
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<APIResponse> handleExpiredJwtException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(APIResponse.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .error("JWT token has expired")
                .build());
    }
    @ExceptionHandler(EmailExistedException.class)
    public ResponseEntity<APIResponse> handleEmailExistedException(EmailExistedException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(APIResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(ex.getMessage())
                .build());
    }
    @ExceptionHandler(UserMistake.class)
    public ResponseEntity<APIResponse> handleEmailExistedException(UserMistake ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(APIResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(ex.getMessage())
                .build());
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<APIResponse> handleBadCredentialsException(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(APIResponse.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .error("Invalid username or password")
                .build());
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<APIResponse> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(APIResponse.builder()
                .status(HttpStatus.FORBIDDEN.value())
                .error("Access Denied")
                .build());
    }
}
