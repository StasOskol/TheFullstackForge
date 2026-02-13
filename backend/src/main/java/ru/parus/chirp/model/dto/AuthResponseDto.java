package ru.parus.chirp.model.dto;

import lombok.Data;

/**
 * AuthResponseDto
 * <p>
 * </p>
 *
 * @author Grachev.D.G  (zhulvern-92@mail.ru)
 * @version 30.01.2026
 */
@Data
public class AuthResponseDto {
    private String token;
    private String type = "Bearer";
    private Long userId;
    private String username;
}
