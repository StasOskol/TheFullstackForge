package ru.parus.chirp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.parus.chirp.model.dto.FollowerDto;

/**
 * SubscriptionService
 * <p>
 * </p>
 *
 * @author Grachev.D.G  (zhulvern-92@mail.ru)
 * @version 27.02.2026
 */
public interface SubscriptionService {
    Page<FollowerDto> index(Pageable pageable);
    FollowerDto subscribe(Long id);
    FollowerDto unsubscribe(Long id);
}
