package com.leaderboard.userservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users/test")
public class TestController {
    @GetMapping("/secure")
    public String secure(@RequestHeader("X-User-Id") String userId,
                         @RequestHeader("X-User-Email") String email,
                         @RequestHeader("X-User-Role") String role
    ) {
        return """
                UserId = %s
                Email = %s
                Role = %s
                """.formatted(
                userId,
                email,
                role
        );
    }
}
