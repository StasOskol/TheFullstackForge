package ru.parus.chirp.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * FollowDto
 * <p>
 *     ДТО Подписчика.
 *     Кто подписан на меня?
 * </p>
 *
 * @author Grachev.D.G  (zhulvern-92@mail.ru)
 * @version 21.02.2026
 */
@Data
public class FollowerDto implements Serializable {
    @Schema(description = "Идентификатор текущего пользователя у которого происходит просмотр")
    private Long userId;
    @Schema(description = "Идентификатор Подписанта")
    private String followerId;
    @Schema(description = "Пользовательское имя Подписанта")
    private String followerUsername;
    @Schema(description = "Когда была совершена подписка")
    private LocalDateTime subscribedAt;
}
