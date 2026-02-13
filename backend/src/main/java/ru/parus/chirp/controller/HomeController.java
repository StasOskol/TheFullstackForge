package ru.parus.chirp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.parus.chirp.service.HomeService;

/**
 * HomeController
 * <p>
 *     Домашний контроллер - отвечает за главную страницу
 *     что видит пользователь
 * </p>
 *
 * @author Grachev.D.G  (zhulvern-92@mail.ru)
 * @version 27.01.2026
 */
@Slf4j
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public String home() {
        return "Hello World!";
    }

}
