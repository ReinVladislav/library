package ru.rein.library.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.rein.library.DTO.LoginRequest;
import ru.rein.library.DTO.LoginResponse;
import ru.rein.library.DTO.RefreshResponse;
import ru.rein.library.DTO.RefreshTokenRequest;
import ru.rein.library.Services.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;


    @PostMapping("/login")
    @Operation(summary = "POST login", description = "Позволяет получить Access token и Refresh Token по логину и паролю")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.attemptLogin(loginRequest.getLogin(), loginRequest.getPassword());
    }

    @PostMapping("/refresh")
    @Operation(summary = "POST refresh", description = "Позволяет получить Access token по Refresh Token")
    public RefreshResponse refresh(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshAccessToken(refreshTokenRequest.getRefreshToken());
    }



}