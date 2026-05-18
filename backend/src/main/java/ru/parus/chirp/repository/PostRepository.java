package ru.parus.chirp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.parus.chirp.model.PostEntity;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
}