package ru.parus.chirp.model.dto.gift;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class GiftDto {
    private Long id;
    private Long fromUserId;
    private String fromUsername; // имя отправителя
    private Long toUserId;
    private String toUsername; // имя получателя
    private GiftTypeDto giftType;
    private String comment;
    private LocalDateTime createdAt;
}