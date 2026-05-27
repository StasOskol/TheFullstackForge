package ru.parus.chirp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.parus.chirp.model.UserEntity;
import ru.parus.chirp.model.dto.UserDto;

public interface UserService {
    UserEntity getCurrentUserEntity();

    Page<UserDto> index(Pageable pageable, String username);

    UserDto show(Long id);

    UserEntity getUserEntityById(Long id);
}
