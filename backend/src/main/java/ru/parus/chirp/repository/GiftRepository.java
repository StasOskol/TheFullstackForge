package ru.parus.chirp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.parus.chirp.model.GiftEntity;

public interface GiftRepository extends JpaRepository<GiftEntity, Long> {
    
    // Найти все подарки, где пользователь - ПОЛУЧАТЕЛЬ
    Page<GiftEntity> findByToUserIdOrderByCreatedAtDesc(Long toUserId, Pageable pageable);
    
    // Найти все подарки, где пользователь - ОТПРАВИТЕЛЬ
    Page<GiftEntity> findByFromUserIdOrderByCreatedAtDesc(Long fromUserId, Pageable pageable);
}