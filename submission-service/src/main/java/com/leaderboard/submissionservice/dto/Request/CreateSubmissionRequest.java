package com.leaderboard.submissionservice.dto.Request;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class CreateSubmissionRequest {
    @NotBlank
    private String code;
}
