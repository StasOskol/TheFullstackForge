package ru.parus.chirp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.parus.chirp.service.FollowerService;

/**
 * FallowerController
 * <p>
 *     Контроллер отвечающий за обработку
 *     подписок и подписчиков
 * </p>
 *
 * @author Grachev.D.G  (zhulvern-92@mail.ru)
 * @version 27.01.2026
 */
@Slf4j
@RestController
@RequestMapping("/followers")
@RequiredArgsConstructor
public class FollowerController {

    private final FollowerService followerService;

    /**
     * Подписка на интересующего пользователя
     * */
    @PostMapping("/subscribe/{id}")
    public ResponseEntity<Object> subscribe(@PathVariable Long id) {
        return ResponseEntity.ok().build();
    }

    /**
     * Отподписка на интересующего пользователя
     * */
    @PostMapping("/unsubscribe/{id}")
    public ResponseEntity<Object> unsubscribe(@PathVariable Long id) {
        return ResponseEntity.ok().build();
    }

}
