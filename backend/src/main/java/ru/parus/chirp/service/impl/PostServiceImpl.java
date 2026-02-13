package ru.parus.chirp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.parus.chirp.exception.NotExistException;
import ru.parus.chirp.exception.PermissionDeniedException;
import ru.parus.chirp.mapper.PostMapper;
import ru.parus.chirp.model.PostEntity;
import ru.parus.chirp.model.UserEntity;
import ru.parus.chirp.model.dto.post.PostDto;
import ru.parus.chirp.repository.PostRepository;
import ru.parus.chirp.service.PostService;
import ru.parus.chirp.service.UserService;

/**
 * PostServiceImpl
 * <p>
 *     Базовая реализация сервиса постов
 * </p>
 *
 * @author Grachev.D.G  (zhulvern-92@mail.ru)
 * @version 30.01.2026
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserService userService;

    @Override
    @Transactional
    public PostDto create(PostDto dto) {
        UserEntity user = userService.getCurrentUserEntity();
        PostEntity postEntity = postMapper.toEntity(dto);
        postEntity.setOwner(user);
        return postMapper.toDto(postRepository.save(postEntity));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostDto> index(final Pageable pageable) {
        Page<PostEntity> pageEntities;
        pageEntities = postRepository.findAll(pageable);
        return new PageImpl<>(pageEntities.getContent().stream().map(postMapper::toDto).toList(),
                        pageable,
                        pageEntities.getContent().size()
                );
    }

    @Override
    @Transactional(readOnly = true)
    public PostDto show(Long id) {
        PostEntity post = postRepository.findById(id)
                .orElseThrow(NotExistException::new);
        return postMapper.toDto(post);
    }

    @Override
    @Transactional(readOnly = true)
    public PostDto update(Long id, PostDto dto) {
        UserEntity user = userService.getCurrentUserEntity();
        PostEntity post = postRepository.findById(id).orElseThrow(NotExistException::new);
        if (post.getOwner().getId().equals(user.getId())) {
            postMapper.patchUpdate(dto, post);
//            postRepository.save(post);
            return postMapper.toDto(post);
        }
        throw new PermissionDeniedException();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        UserEntity user = userService.getCurrentUserEntity();
        PostEntity post = postRepository.findById(id)
                .orElseThrow(NotExistException::new);
        if (post.getOwner().getId().equals(user.getId())) {
            postRepository.delete(post);
        }
        throw new PermissionDeniedException();
    }
}
