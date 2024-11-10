package ru.rein.library.DTO;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {
    private String token;
    private String refreshToken;
}

