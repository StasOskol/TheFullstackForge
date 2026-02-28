package ru.parus.chirp.model.dto;

import java.io.Serializable;
import lombok.Data;

/**
 * UserDto
 * <p>
 * </p>
 *
 * @author Grachev.D.G  (zhulvern-92@mail.ru)
 * @version 21.02.2026
 */
@Data
public class UserDto implements Serializable {
 private Long id;
 private String username;
}
