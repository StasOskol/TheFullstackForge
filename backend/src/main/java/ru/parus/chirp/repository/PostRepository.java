package ru.parus.chirp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.parus.chirp.model.PostEntity;

/**
 * PostRepository
 * <p>
 * </p>
 *
 * @author Grachev.D.G  (zhulvern-92@mail.ru)
 * @version 30.01.2026
 */
public interface PostRepository extends JpaRepository<PostEntity, Long> {
}
