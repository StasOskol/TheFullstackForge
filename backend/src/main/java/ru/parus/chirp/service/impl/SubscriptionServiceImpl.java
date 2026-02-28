package ru.parus.chirp.service.impl;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.parus.chirp.exception.NotExistException;
import ru.parus.chirp.exception.NotExistUserException;
import ru.parus.chirp.mapper.FollowerMapper;
import ru.parus.chirp.model.FollowerEntity;
import ru.parus.chirp.model.UserEntity;
import ru.parus.chirp.model.dto.FollowerDto;
import ru.parus.chirp.repository.FollowerRepository;
import ru.parus.chirp.repository.UserRepository;
import ru.parus.chirp.service.SubscriptionService;
import ru.parus.chirp.service.UserService;

/**
 * SubscriptionService
 * <p>
 *     Ответственный сервис за работу с подписками
 *     (на кого пользователь сам подписан)
 * </p>
 *
 * @author Grachev.D.G  (zhulvern-92@mail.ru)
 * @version 27.02.2026
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private final FollowerRepository followerRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final FollowerMapper followerMapper;


    @Override
    @Transactional(readOnly = true)
    public Page<FollowerDto> index(Pageable pageable) {
        UserEntity currentUserEntity = userService.getCurrentUserEntity();
        return followerRepository.findByFollower(currentUserEntity, pageable)
                .map(followerMapper::toDtoSubscription);
    }

    /**
     * Возращаем сущность чтобы сообщить пользователю
     * на кого произошла подписка
     * */
    @Override
    @Transactional
    public FollowerDto subscribe(Long id) {
        UserEntity currentUserEntity = userService.getCurrentUserEntity();
        Optional<UserEntity> followUser = userRepository.findById(id);
        if (followUser.isEmpty()) {
            throw new NotExistUserException("Нет такого пользователя");
        }
        if (currentUserEntity.getId().equals(followUser.get().getId())) {
            throw new RuntimeException("Нельзя подписаться самого на себя");
        }
        if (followerRepository.existsByUserAndFollower(currentUserEntity, followUser.get())) {
            throw new RuntimeException("Подписка уже оформлена");
        }
        FollowerEntity follower = FollowerEntity.builder()
                .follower(followUser.get())
                .user(currentUserEntity)
                .build();
        followerRepository.save(follower);
        return followerMapper.toDtoSubscription(follower);
    }

    /**
     * Возращаем объект, чтобы дать возможность
     * пользотелю на фронте отменить действие
     * */
    @Override
    @Transactional
    public FollowerDto unsubscribe(Long id) {
        UserEntity currentUserEntity = userService.getCurrentUserEntity();
        UserEntity followUser = userRepository.findById(id).orElseThrow(NotExistUserException::new);
        Optional<FollowerEntity> followerLink = followerRepository.findByUserAndFollower(currentUserEntity, followUser);
        if (followerLink.isPresent()) {
            followerRepository.delete(followerLink.get());
            return followerMapper.toDtoSubscription(followerLink.get());
        }
        throw new NotExistException("Такой связи не существует");
    }
}
