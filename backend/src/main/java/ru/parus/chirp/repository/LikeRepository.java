package ru.parus.chirp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.parus.chirp.model.LikeEntity;
import ru.parus.chirp.model.LikeId;

/**
 * LikeRepository
 * <p>
 *     Репозиторий для управления лайками
 * </p>
 *
 * @author Grachev.D.G  (zhulvern-92@mail.ru)
 * @version 30.01.2026
 */
public interface LikeRepository extends JpaRepository<LikeEntity, LikeId> {
    boolean existsByUserIdAndPostId(Long userId, Long postId);
    void deleteByUserIdAndPostId(Long userId, Long postId);
}
