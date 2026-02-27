package ru.parus.chirp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.parus.chirp.model.UserEntity;
import ru.parus.chirp.model.dto.UserDto;

/**
 * UserService
 * <p>
 * </p>
 *
 * @author Grachev.D.G  (zhulvern-92@mail.ru)
 * @version 30.01.2026
 */
public interface UserService {
    UserEntity getCurrentUserEntity();

    Page<UserDto> index(Pageable pageable, String username);

    UserDto show(Long id);
}
