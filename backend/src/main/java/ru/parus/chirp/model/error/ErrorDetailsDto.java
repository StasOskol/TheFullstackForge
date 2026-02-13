package ru.parus.chirp.model.error;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Builder;
import lombok.Data;

/**
 * ErrorDetailDto
 * <p>
 *     Класс для описания ошибки для фронта
 * </p>
 *
 * @author Grachev.D.G  zhulvern-92@mail.ru
 */

@Data
@Builder
@JsonTypeName("ErrorDetails")
public class ErrorDetailsDto {
    private int statusCode;
    private String message;
    private String localizedMessage;
    private String details;
}
