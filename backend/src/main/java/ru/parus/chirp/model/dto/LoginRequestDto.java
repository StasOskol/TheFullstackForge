package ru.parus.chirp.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * LoginRequest
 * <p>
 * </p>
 *
 * @author Grachev.D.G  (zhulvern-92@mail.ru)
 * @version 30.01.2026
 */
@Data
public class LoginRequestDto {
    @NotBlank(message = "Логин или email не может быть пустым")
    private String login; // Можно принимать username или email

    @NotBlank(message = "Пароль не может быть пустым")
    private String password;
}
