package ru.parus.chirp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.parus.chirp.model.dto.gift.CreateGiftRequest;
import ru.parus.chirp.model.dto.gift.GiftDto;
import ru.parus.chirp.service.GiftService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/gifts")
@RequiredArgsConstructor
public class GiftController {
    
    private final GiftService giftService;
    
    @PostMapping("/send")
    @ResponseStatus(HttpStatus.CREATED)
    public GiftDto sendGift(@Valid @RequestBody CreateGiftRequest request) {  // ← @RequestBody обязательно!
        return giftService.sendGift(request);
    }
    
    @GetMapping("/received")
    public Page<GiftDto> getMyReceivedGifts(@PageableDefault(size = 20) Pageable pageable) {
        return giftService.getMyReceivedGifts(pageable);
    }
    
    @GetMapping("/sent")
    public Page<GiftDto> getMySentGifts(@PageableDefault(size = 20) Pageable pageable) {
        return giftService.getMySentGifts(pageable);
    }
    
    @GetMapping("/{id}")
    public GiftDto getGiftById(@PathVariable Long id) {
        return giftService.getGiftById(id);
    }
}