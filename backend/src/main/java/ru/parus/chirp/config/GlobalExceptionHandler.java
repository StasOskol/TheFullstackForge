package ru.parus.chirp.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import ru.parus.chirp.exception.AccessDeniedException;
import ru.parus.chirp.exception.ConflictException;
import ru.parus.chirp.exception.NotExistException;
import ru.parus.chirp.exception.PermissionDeniedException;
import ru.parus.chirp.model.error.ErrorDetailsDto;

/**
 * GlobalHandler
 * <p>
 *     Глобальный обработчик исключений в рамках системы
 *     для обертки в ответ потребителя
 * </p>
 *
 * @author Grachev.D.G  (zhulvern-92@mail.ru)
 * @version 31.01.2026
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({AccessDeniedException.class, ConflictException.class, NotExistException.class, LockedException.class,
    PermissionDeniedException.class, UsernameNotFoundException.class})
    public ResponseEntity<ErrorDetailsDto> internalServerError(RuntimeException ex, WebRequest request) {
        log.error("INTERNAL_SERVER_ERROR: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(ErrorDetailsDto
                .builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage())
                .localizedMessage(ex.getLocalizedMessage())
                .details(request.getDescription(false))
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
