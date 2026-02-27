package ru.parus.chirp.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.parus.chirp.model.FollowerEntity;
import ru.parus.chirp.model.UserEntity;

/**
 * FollowerRepository
 * <p>
 *     Репозиторий управляет подписками
 *     Многие ко многим. Суть в том, что
 *     пользователь имеет множество подписок,
 *     при этом на него может быть подписано множество
 *     людей. Потому банально реверсивно узнаем интересующую
 *     информацию,
 * </p>
 *
 * @author Grachev.D.G  (zhulvern-92@mail.ru)
 * @version 30.01.2026
 */
public interface FollowerRepository extends JpaRepository<FollowerEntity, Long> {
    // Когда надо узнать есть ли такая связь вообще
    boolean existsByUserAndFollower(UserEntity user, UserEntity follower);

    // Когда эту связь надо вытащить
    Optional<FollowerEntity> findByUserAndFollower(UserEntity user, UserEntity follower);

    // Получаем список на кого пользователь подписан сам
    List<FollowerEntity> findByUser(UserEntity user);
    // ТО же самое только с постраничной навигацией
    Page<FollowerEntity> findByUser(UserEntity user, Pageable pageable);


    // На кого пользователь подписан сам как подписчик
    List<FollowerEntity> findByFollower(UserEntity follower);
    // ТО же самое только с постраничной навигацией
    Page<FollowerEntity> findByFollower(UserEntity follower,  Pageable pageable);

}
