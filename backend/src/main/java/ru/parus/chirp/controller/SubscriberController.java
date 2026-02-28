package ru.parus.chirp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.parus.chirp.model.dto.FollowerDto;
import ru.parus.chirp.service.SubscriberService;

/**
 * SubscriberController
 * <p>
 *     Контроллер отвечающий за обработку
 *     работы с подписчиками пользователя
 *     (Кто на него подписан)
 * </p>
 *
 * @author Grachev.D.G  (zhulvern-92@mail.ru)
 * @version 27.01.2026
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/subscribers", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class SubscriberController {

    private final SubscriberService subscriberService;

    /**
     * Просмотр подписчиков с пагинацией
     * */
    @Operation(summary = "Просмотр подписчиков с пагинацией",
            description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный ответ"),
    })
    @GetMapping("/")
    public ResponseEntity<Page<FollowerDto>> index(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(subscriberService.index(pageable));
    }

    /**
     * Удаляем себя из списоа подписок у кокретного пользователя
     * */
    @Operation(summary = "Удаляем себя из списоа подписок у кокретного пользователя",
            description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный ответ"),
    })
    @DeleteMapping("/removeSubscribe/{id}")
    public ResponseEntity<Object> removeSubscribe(@PathVariable Long id) {
        return ResponseEntity.ok(subscriberService.removeSubscribe(id));
    }


}
