package com.leaderboard.submissionservice.Exception;

public class ContestNotFoundException extends RuntimeException {
    public ContestNotFoundException(String message) {
        super(message);
    }
}
