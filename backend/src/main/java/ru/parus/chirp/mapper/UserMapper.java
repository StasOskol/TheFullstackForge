package ru.parus.chirp.mapper;

import org.mapstruct.Mapper;
import ru.parus.chirp.model.UserEntity;
import ru.parus.chirp.model.dto.UserDto;

/**
 * UserMapper
 * <p>
 *     Маппер для работы с сущностями
 *     пользователей
 * </p>
 *
 * @author Grachev.D.G  (zhulvern-92@mail.ru)
 * @version 27.02.2026
 */
@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(UserEntity entity);
}
