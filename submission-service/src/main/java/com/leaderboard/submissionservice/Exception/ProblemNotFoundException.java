package com.leaderboard.submissionservice.Exception;

public class ProblemNotFoundException extends RuntimeException {
    public ProblemNotFoundException(String message) {
        super(message);
    }
}
