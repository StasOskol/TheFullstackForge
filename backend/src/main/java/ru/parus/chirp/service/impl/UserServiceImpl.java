package ru.parus.chirp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.parus.chirp.model.UserEntity;
import ru.parus.chirp.service.UserService;

/**
 * UserServiceImpl
 * <p>
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

    @Override
    public UserEntity getCurrentUserEntity() {
        return authService.getCurrentUserEntity();
    }
}
