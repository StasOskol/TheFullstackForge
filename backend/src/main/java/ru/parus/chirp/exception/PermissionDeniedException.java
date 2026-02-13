package ru.parus.chirp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static ru.parus.chirp.exception.DicMessageException.PERMISSION_DENIED_EXCEPTION;


/**
 * PermissionDeniedException
 * <p>
 *     Исключение для случая если есть доступ к системе (через openID)
 *     но прав доступа недостаточно
 * </p>
 *
 * @author Grachev.D.G  zhulvern-92@mail.ru
 */
@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = PERMISSION_DENIED_EXCEPTION)
public class PermissionDeniedException extends RuntimeException {
    public PermissionDeniedException() {
        super(PERMISSION_DENIED_EXCEPTION);
    }

    public PermissionDeniedException(String message) {
        super(message);
    }

    public PermissionDeniedException(String message, Throwable cause) {
        super(message, cause);
    }

    public PermissionDeniedException(Throwable cause) {
        super(cause);
    }

    public PermissionDeniedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
