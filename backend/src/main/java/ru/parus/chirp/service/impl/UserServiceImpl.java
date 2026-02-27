package ru.parus.chirp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.parus.chirp.exception.NotExistUserException;
import ru.parus.chirp.mapper.UserMapper;
import ru.parus.chirp.model.UserEntity;
import ru.parus.chirp.model.dto.UserDto;
import ru.parus.chirp.repository.UserRepository;
import ru.parus.chirp.service.UserService;

/**
 * UserServiceImpl
 * <p>
 *     Cервис для работы с пользователями системы
 * </p>
 *
 * @author Grachev.D.G  (zhulvern-92@mail.ru)
 * @version 30.01.2026
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AuthService authService;

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserEntity getCurrentUserEntity() {
        return authService.getCurrentUserEntity();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserDto> index(Pageable pageable, String username) {
        if (username == null) {
            return userRepository.findAll(pageable).map(userMapper::toDto);
        }
        return userRepository.findByIdContainsIgnoreCase(pageable, username)
                .map(userMapper::toDto);

    }

    @Override
    @Transactional(readOnly = true)
    public UserDto show(Long id) {
        return userRepository.findById(id).map(userMapper::toDto)
                .orElseThrow(NotExistUserException::new);
    }
}
