package ru.parus.chirp.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.parus.chirp.model.GiftEntity;
import ru.parus.chirp.model.GiftTypeEntity;
import ru.parus.chirp.model.UserEntity;
import ru.parus.chirp.model.dto.gift.GiftDto;
import ru.parus.chirp.model.dto.gift.GiftTypeDto;
import ru.parus.chirp.repository.GiftTypeRepository;
import ru.parus.chirp.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class GiftMapper {

    private final UserRepository userRepository;
    private final GiftTypeRepository giftTypeRepository;

    public GiftDto toDto(GiftEntity entity) {
        if (entity == null) return null;
        
        GiftDto dto = new GiftDto();
        dto.setId(entity.getId());
        dto.setFromUserId(entity.getFromUserId());
        dto.setToUserId(entity.getToUserId());
        dto.setComment(entity.getComment());
        dto.setCreatedAt(entity.getCreatedAt());

        // Получаем имя отправителя
        UserEntity fromUser = userRepository.findById(entity.getFromUserId()).orElse(null);
        if (fromUser != null) {
            dto.setFromUsername(fromUser.getUsername());
        }

        // Получаем имя получателя
        UserEntity toUser = userRepository.findById(entity.getToUserId()).orElse(null);
        if (toUser != null) {
            dto.setToUsername(toUser.getUsername());
        }

        // Получаем тип подарка
        GiftTypeEntity giftType = giftTypeRepository.findById(entity.getGiftTypeId()).orElse(null);
        if (giftType != null) {
            GiftTypeDto typeDto = new GiftTypeDto();
            typeDto.setId(giftType.getId());
            typeDto.setName(giftType.getName());
            typeDto.setDescription(giftType.getDescription());
            typeDto.setIconUrl(giftType.getIconUrl());
            dto.setGiftType(typeDto);
        }

        return dto;
    }
}