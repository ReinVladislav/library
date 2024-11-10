package ru.rein.library.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Модель для аутентификации пользователя")
public class LoginRequest {
    @Schema(description = "Логин пользователя", example = "user123")
    private String login;
    @Schema(description = "Пароль пользователя", example = "123")
    private String password;

}
