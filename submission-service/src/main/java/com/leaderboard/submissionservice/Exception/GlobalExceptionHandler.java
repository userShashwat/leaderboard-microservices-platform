package com.leaderboard.submissionservice.Exception;

import com.leaderboard.submissionservice.common.ApiResponse;
import org.springframework.boot.autoconfigure.batch.BatchTaskExecutor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ContestNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> contest(ContestNotFoundException ex){
        return ResponseEntity.badRequest().body(
                new ApiResponse<>(
                        false,
                        ex.getMessage(),
                        null
                )
        );
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleException(Exception ex) {
        return ResponseEntity.internalServerError().body(
                new ApiResponse<>(
                        false,
                        ex.getMessage(),
                        null
                )
        );
    }
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiResponse<String>> handleUnauthorized(UnauthorizedException ex){
        return ResponseEntity.status(403).body(
                new ApiResponse<>(
                        false,
                        ex.getMessage(),
                        null
                )
        );
    }
    @ExceptionHandler(ProblemNotFoundException.class)
    public ResponseEntity<ApiResponse<String>>  handleProblemNotFound(ProblemNotFoundException ex){
        return ResponseEntity.badRequest().body(
                new ApiResponse<>(
                        false,
                        ex.getMessage(),
                        null
                )
        );

    }
    @ExceptionHandler(SubmissionNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleSubmissionNotFound(ProblemNotFoundException ex){
        return ResponseEntity.badRequest().body(
                new ApiResponse<>(
                        false,
                        ex.getMessage(),
                        null
                )
        );
    }
}
