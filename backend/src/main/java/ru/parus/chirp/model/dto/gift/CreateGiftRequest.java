package ru.parus.chirp.model.dto.gift;

import lombok.Data;

@Data
public class CreateGiftRequest {
    private Long toUserId;
    private Long giftTypeId;
    private String comment;
}