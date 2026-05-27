package ru.parus.chirp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.parus.chirp.model.dto.gift.CreateGiftRequest;
import ru.parus.chirp.model.dto.gift.GiftDto;

public interface GiftService {
    GiftDto sendGift(CreateGiftRequest request);
    Page<GiftDto> getMyReceivedGifts(Pageable pageable);
    Page<GiftDto> getMySentGifts(Pageable pageable);
    GiftDto getGiftById(Long id);
}