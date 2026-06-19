package com.leaderboard.submissionservice.Exception;

public class SubmissionNotFoundException extends RuntimeException {
    public SubmissionNotFoundException(String message) {
        super(message);
    }
}
