package ru.parus.chirp.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.parus.chirp.model.PostEntity;
import ru.parus.chirp.model.dto.post.PostDto;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "userId", source = "owner.id")
    PostDto toDto(PostEntity entity);

    PostEntity toEntity(PostDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchUpdate(PostDto dto, @MappingTarget PostEntity entity);
}
