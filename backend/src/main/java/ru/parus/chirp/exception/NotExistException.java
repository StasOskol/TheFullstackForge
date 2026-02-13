package ru.parus.chirp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static ru.parus.chirp.exception.DicMessageException.NOT_EXIST_EXCEPTION;


/**
 * NotExistException
 * <p>
 * Исключение если сущности не существует
 * </p>
 *
 * @author Grachev.D.G  zhulvern-92@mail.ru
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = NOT_EXIST_EXCEPTION)
public class NotExistException extends RuntimeException {

    public NotExistException() {
        super(NOT_EXIST_EXCEPTION);
    }

    public NotExistException(String message) {
        super(message);
    }

    public NotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotExistException(Throwable cause) {
        super(cause);
    }

    public NotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
