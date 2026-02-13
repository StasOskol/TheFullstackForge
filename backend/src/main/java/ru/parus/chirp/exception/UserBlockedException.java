package ru.parus.chirp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static ru.parus.chirp.exception.DicMessageException.CHAT_BLOCKED_EXCEPTION;


/**
 * NotExistException
 * <p>
 * Учетная запись заблокирована заблокирован
 * </p>
 *
 * @author Grachev.D.G  zhulvern-92@mail.ru
 * @version 12.11.2024 13:20
 */
@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = CHAT_BLOCKED_EXCEPTION)
public class UserBlockedException extends RuntimeException {

    public UserBlockedException() {
        super(CHAT_BLOCKED_EXCEPTION);
    }

    public UserBlockedException(String message) {
        super(message);
    }

    public UserBlockedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserBlockedException(Throwable cause) {
        super(cause);
    }

    public UserBlockedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
