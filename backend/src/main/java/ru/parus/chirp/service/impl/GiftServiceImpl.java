package ru.parus.chirp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.parus.chirp.exception.NotExistException;
import ru.parus.chirp.mapper.GiftMapper;
import ru.parus.chirp.model.GiftEntity;
import ru.parus.chirp.model.UserEntity;
import ru.parus.chirp.model.dto.gift.CreateGiftRequest;
import ru.parus.chirp.model.dto.gift.GiftDto;
import ru.parus.chirp.repository.GiftRepository;
import ru.parus.chirp.repository.GiftTypeRepository;
import ru.parus.chirp.service.GiftService;
import ru.parus.chirp.service.UserService;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class GiftServiceImpl implements GiftService {
    
    private final GiftRepository giftRepository;
    private final GiftTypeRepository giftTypeRepository;
    private final GiftMapper giftMapper;
    private final UserService userService;
    
    @Override
    @Transactional
    public GiftDto sendGift(CreateGiftRequest request) {  // ← Принимаем объект
        UserEntity currentUser = userService.getCurrentUserEntity();
        
        // Извлекаем данные из объекта request
        Long toUserId = request.getToUserId();        // ← получаем ID получателя
        Long giftTypeId = request.getGiftTypeId();    // ← получаем ID типа подарка
        String comment = request.getComment();        // ← получаем комментарий
        
        // Проверяем существование получателя
        UserEntity toUser = userService.getUserEntityById(toUserId);
        if (toUser == null) {
            throw new NotExistException("Получатель не найден");
        }
        
        // Проверяем существование типа подарка
        if (!giftTypeRepository.existsById(giftTypeId)) {
            throw new NotExistException("Тип подарка не найден");
        }
        
        // Нельзя отправить подарок самому себе
        if (currentUser.getId().equals(toUserId)) {
            throw new IllegalArgumentException("Нельзя отправить подарок самому себе");
        }
        
        // Создаем подарок
        GiftEntity gift = new GiftEntity();
        gift.setFromUserId(currentUser.getId());
        gift.setToUserId(toUserId);
        gift.setGiftTypeId(giftTypeId);
        gift.setComment(comment);
        gift.setCreatedAt(LocalDateTime.now());
        
        GiftEntity saved = giftRepository.save(gift);
        log.info("Подарок отправлен: от {} к {}", currentUser.getId(), toUserId);
        
        return giftMapper.toDto(saved);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<GiftDto> getMyReceivedGifts(Pageable pageable) {
        UserEntity currentUser = userService.getCurrentUserEntity();
        Page<GiftEntity> gifts = giftRepository.findByToUserIdOrderByCreatedAtDesc(
                currentUser.getId(), pageable);
        
        return new PageImpl<>(
                gifts.getContent().stream()
                        .map(giftMapper::toDto)
                        .toList(),
                pageable,
                gifts.getTotalElements()
        );
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<GiftDto> getMySentGifts(Pageable pageable) {
        UserEntity currentUser = userService.getCurrentUserEntity();
        Page<GiftEntity> gifts = giftRepository.findByFromUserIdOrderByCreatedAtDesc(
                currentUser.getId(), pageable);
        
        return new PageImpl<>(
                gifts.getContent().stream()
                        .map(giftMapper::toDto)
                        .toList(),
                pageable,
                gifts.getTotalElements()
        );
    }
    
    @Override
    @Transactional(readOnly = true)
    public GiftDto getGiftById(Long id) {
        GiftEntity gift = giftRepository.findById(id)
                .orElseThrow(() -> new NotExistException("Подарок не найден"));
        return giftMapper.toDto(gift);
    }
}