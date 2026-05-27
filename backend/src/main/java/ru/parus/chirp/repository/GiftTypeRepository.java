package ru.parus.chirp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.parus.chirp.model.GiftTypeEntity;

public interface GiftTypeRepository extends JpaRepository<GiftTypeEntity, Long> {
}