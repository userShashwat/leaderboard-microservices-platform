package com.leaderboard.userservice.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class RegisterRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @Email
    private String email;

    @NotBlank
    @Size(min = 8, max = 50)
    private String password;
}
