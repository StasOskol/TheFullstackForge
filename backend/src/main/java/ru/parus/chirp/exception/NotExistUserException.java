package ru.parus.chirp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static ru.parus.chirp.exception.DicMessageException.NOT_EXIST_USER_EXCEPTION;


/**
 * NotExistException
 * <p>
 * Исключение если сущности не существует
 * </p>
 *
 * @author Grachev.D.G  zhulvern-92@mail.ru
 */
@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = NOT_EXIST_USER_EXCEPTION)
public class NotExistUserException extends RuntimeException {

    public NotExistUserException() {
        super(NOT_EXIST_USER_EXCEPTION);
    }

    public NotExistUserException(String message) {
        super(message);
    }

    public NotExistUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotExistUserException(Throwable cause) {
        super(cause);
    }

    public NotExistUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
