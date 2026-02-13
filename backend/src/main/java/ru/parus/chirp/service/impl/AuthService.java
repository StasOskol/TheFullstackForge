package ru.parus.chirp.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.parus.chirp.exception.PermissionDeniedException;
import ru.parus.chirp.model.UserEntity;
import ru.parus.chirp.model.dto.AuthResponseDto;
import ru.parus.chirp.model.dto.LoginRequestDto;
import ru.parus.chirp.model.dto.RegisterRequestDto;
import ru.parus.chirp.repository.UserRepository;
import ru.parus.chirp.security.JwtTokenProvider;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public AuthResponseDto register(RegisterRequestDto request) {
        // Проверяем уникальность
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Имя пользователя уже занято");
        }

        // Создаем пользователя
        UserEntity user = UserEntity.builder().build();
        user.setUsername(request.getUsername());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        // Генерируем токен
        String token = jwtTokenProvider.generateToken(user.getUsername());

        return createAuthResponse(token, user);
    }

    public AuthResponseDto login(LoginRequestDto request) {
        // Аутентифицируем пользователя
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),  // Может быть username или email
                        request.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Получаем пользователя
        UserEntity user = (UserEntity) authentication.getPrincipal();

        // Генерируем токен
        String token = jwtTokenProvider.generateToken(user.getUsername());

        return createAuthResponse(token, user);
    }

    public UserEntity getCurrentUserEntity() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new PermissionDeniedException("Пользователь не авторизован");
        }
        if (authentication.getPrincipal() instanceof UserEntity) {
            return (UserEntity) authentication.getPrincipal();
        }
        throw new PermissionDeniedException("Пользователь не авторизован");
    }

    private AuthResponseDto createAuthResponse(String token, UserEntity user) {
        AuthResponseDto response = new AuthResponseDto();
        response.setToken(token);
        response.setUserId(user.getId());
        response.setUsername(user.getUsername());
        return response;
    }
}