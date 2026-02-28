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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.parus.chirp.model.dto.FollowerDto;
import ru.parus.chirp.service.SubscriptionService;

/**
 * SubscriptionController
 * <p>
 *     Контроллер отвечающий за
 *     подписки самого пользователя
 *     (на кого он подписан)
 * </p>
 *
 * @author Grachev.D.G  (zhulvern-92@mail.ru)
 * @version 27.02.2026
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/subscriptions", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class SubscriptionController {
    private final SubscriptionService service;

    @Operation(summary = "Просмотр списка подписок пользователя",
            description = "На кого именно он подписан")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный ответ"),
    })
    @GetMapping("/")
    public ResponseEntity<Page<FollowerDto>> index(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(service.index(pageable));
    }

    @Operation(summary = "Подписка на определенного пользователя",
            description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный ответ"),
    })
    @PostMapping("/subscribe/{id}")
    public ResponseEntity<FollowerDto> subscribe(@PathVariable Long id) {
        return ResponseEntity.ok(service.subscribe(id));
    }

    @Operation(summary = "Отписка от определенного пользователя",
            description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный ответ"),
    })
    @PostMapping("/unsubscribe/{id}")
    public ResponseEntity<FollowerDto> unsubscribe(@PathVariable  Long id) {
        return ResponseEntity.ok(service.unsubscribe(id));
    }

}
