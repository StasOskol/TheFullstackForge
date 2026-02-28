package ru.parus.chirp.service.impl;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.parus.chirp.exception.NotExistUserException;
import ru.parus.chirp.mapper.FollowerMapper;
import ru.parus.chirp.model.FollowerEntity;
import ru.parus.chirp.model.UserEntity;
import ru.parus.chirp.model.dto.FollowerDto;
import ru.parus.chirp.repository.FollowerRepository;
import ru.parus.chirp.repository.UserRepository;
import ru.parus.chirp.service.SubscriberService;
import ru.parus.chirp.service.UserService;

/**
 * SubscriberServiceImpl
 * <p>
 *     Обработчик кто подписан
 *     на самого пользователя
 * </p>
 *
 * @author Grachev.D.G  (zhulvern-92@mail.ru)
 * @version 30.01.2026
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriberServiceImpl implements SubscriberService {

    private final FollowerRepository followerRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final FollowerMapper followerMapper;

    /**
     * Получаем список пользователей что подписаны на нас
     * */
    @Override
    @Transactional(readOnly = true)
    public Page<FollowerDto> index(Pageable pageable) {
        UserEntity currentUserEntity = userService.getCurrentUserEntity();
        return followerRepository.findByFollower(currentUserEntity, pageable)
                .map(followerMapper::toDtoSubscriber);
    }


    /**
     * Удаляем себя из чужого списка
     * Замедьте, нельзя удалить пользователя у другого пользователя
     * */
    @Override
    @Transactional
    public FollowerDto removeSubscribe(Long id) {
        UserEntity currentUserEntity = userService.getCurrentUserEntity();
        UserEntity follower = userRepository.findById(id).orElseThrow(NotExistUserException::new);
        // Так как мы хотим удалить себя у кого то - свапаем порядок параметров
        Optional<FollowerEntity> supscriberLink = followerRepository.findByUserAndFollower(follower, currentUserEntity);
        supscriberLink.ifPresent(followerRepository::delete);
        if (supscriberLink.isPresent()) {
            return followerMapper.toDtoSubscriber(supscriberLink.get());
        }
        throw new NotExistUserException("Такой связи не существует");
    }

}
