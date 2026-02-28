package ru.parus.chirp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.parus.chirp.exception.NotExistUserException;
import ru.parus.chirp.model.UserEntity;
import ru.parus.chirp.repository.FollowerRepository;
import ru.parus.chirp.repository.UserRepository;
import ru.parus.chirp.service.NotificationService;

/**
 * NotificationSimpleServiceImpl
 * <p>
 *     Шуточный сервис для отправки уведомлений подписчикам пользователя
 * </p>
 *
 * @author Grachev.D.G  (zhulvern-92@mail.ru)
 * @version 27.02.2026
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationSimpleServiceImpl implements NotificationService {

    private final UserRepository userRepository;
    private final FollowerRepository followerRepository;

    @Override
    @Async("asyncExecutor")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void notifyAsyncNewPost(Long userId) {
        // Чтобы не крахнуть поток, оборачиваем в try/catch
        try {
            UserEntity user = userRepository.findById(userId).orElseThrow(NotExistUserException::new);
            followerRepository.findByFollower(user).forEach(follower -> {
                log.info("{} Получил уведомление о новом посте!", follower.getUser().getUsername());
            });
        } catch (Exception e) {
            log.error("Ошибка при отправке уведомлений для userId: {}", userId, e);
        }

        log.debug("Завершена асинхронная отправка уведомлений для userId: {}", userId);
    }
}
