package ru.parus.chirp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.parus.chirp.model.dto.post.PostDto;

/**
 * PostService
 * <p>
 *      Интерфейс для работы с постами
 * </p>
 *
 * @author Grachev.D.G  (zhulvern-92@mail.ru)
 * @version 30.01.2026
 */
public interface PostService {
    PostDto create(final PostDto dto);
    Page<PostDto> index(final Pageable pageable);
    PostDto show(Long id);
    PostDto update(Long id, final PostDto dto);
    void delete(Long id);
}
