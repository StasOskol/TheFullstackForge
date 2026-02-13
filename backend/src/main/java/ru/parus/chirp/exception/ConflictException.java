package ru.parus.chirp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static ru.parus.chirp.exception.DicMessageException.CONFLICT_EXCEPTION;


/**
 * ConflictCreateException
 * <p>
 * </p>
 *
 * @author Grachev.D.G  zhulvern-92@mail.ru
 */
@ResponseStatus(code = HttpStatus.CONFLICT, reason = CONFLICT_EXCEPTION)
public class ConflictException extends RuntimeException {

    public ConflictException() {
        super(CONFLICT_EXCEPTION);
    }
}
