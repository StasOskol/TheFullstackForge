package ru.parus.chirp.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.parus.chirp.model.PostEntity;
import ru.parus.chirp.model.dto.post.PostDto;

/**
 * PostMapper
 * <p>
 *     Маппер для преобразования постов из сущности в ДТО и обратно
 * </p>
 *
 * @author Grachev.D.G  (zhulvern-92@mail.ru)
 * @version 31.01.2026
 */
@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "userId", source = "owner.id")
    PostDto toDto(PostEntity entity);

    PostEntity toEntity(PostDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchUpdate(PostDto dto, @MappingTarget PostEntity entity);
}
