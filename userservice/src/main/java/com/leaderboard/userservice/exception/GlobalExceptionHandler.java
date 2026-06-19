package com.leaderboard.userservice.exception;

import com.leaderboard.userservice.common.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<String>> handleUserExists(
            UserAlreadyExistsException ex){
        return ResponseEntity.badRequest().body(
                new ApiResponse<>(
                        false,
                        ex.getMessage(),
                        null
                )
        );
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleGeneral(
            Exception ex
    ) {

        return ResponseEntity.internalServerError().body(
                new ApiResponse<>(
                        false,
                        ex.getMessage(),
                        null
                )
        );
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleUserNotFound(){
        return ResponseEntity.badRequest().body(
                new ApiResponse<>(
                        false,
                        "user not found",
                        null
                )
        );
    }
}
