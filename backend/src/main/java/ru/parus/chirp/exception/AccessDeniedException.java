package ru.parus.chirp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static ru.parus.chirp.exception.DicMessageException.ACCESS_DENIED_EXCEPTION;


/**
 * PermissionDeniedException
 * <p>
 *     Исключение для несанкционированного доступа
 * </p>
 *
 * @author Grachev.D.G  zhulvern-92@mail.ru
 */
@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = ACCESS_DENIED_EXCEPTION)
public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException() {
        super(ACCESS_DENIED_EXCEPTION);
    }

    public AccessDeniedException(String message) {
        super(message);
    }

    public AccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessDeniedException(Throwable cause) {
        super(cause);
    }

    public AccessDeniedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
