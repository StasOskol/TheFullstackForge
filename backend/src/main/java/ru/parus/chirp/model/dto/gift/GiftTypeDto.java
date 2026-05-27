package ru.parus.chirp.model.dto.gift;

import lombok.Data;

@Data
public class GiftTypeDto {
    private Long id;
    private String name;
    private String description;
    private String iconUrl;
}