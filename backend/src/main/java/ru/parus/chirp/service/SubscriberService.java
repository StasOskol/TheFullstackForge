package ru.parus.chirp.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.parus.chirp.model.dto.FollowerDto;

/**
 * SubscriberService
 * <p>
 *     Интерфейс для управления подписками клиента
 * </p>
 *
 * @author Grachev.D.G  (zhulvern-92@mail.ru)
 * @version 30.01.2026
 */
public interface SubscriberService {
    // Просмотреть список
    Page<FollowerDto> index(Pageable pageable);
    // Удяляем себя из списка подписчика - прородитель блокировки (черного списка)
    FollowerDto removeSubscribe(Long id);
}
