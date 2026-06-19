package com.leaderboard.submissionservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateContestRequest {
    @NotBlank
    private String name;

    private String description;
}
