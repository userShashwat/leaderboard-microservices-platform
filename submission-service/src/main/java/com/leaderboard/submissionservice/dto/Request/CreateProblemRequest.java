package com.leaderboard.submissionservice.dto.Request;

import com.leaderboard.submissionservice.domain.emun.Difficulty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProblemRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private Difficulty difficulty;

    @NotNull
    private Integer maxScore;
    private String sampleInput;

    private String sampleOutput;
}
