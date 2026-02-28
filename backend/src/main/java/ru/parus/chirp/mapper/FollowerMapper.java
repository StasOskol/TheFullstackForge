package ru.parus.chirp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.parus.chirp.model.FollowerEntity;
import ru.parus.chirp.model.dto.FollowerDto;

/**
 * FollowerMapper
 * <p>
 * </p>
 *
 * @author Grachev.D.G  (zhulvern-92@mail.ru)
 * @version 21.02.2026
 */
@Mapper(componentModel = "spring")
public interface FollowerMapper {

    /**
     * Маппируем пользователя на которого подписаны
     * */
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "followerId", source = "follower.id")
    @Mapping(target = "followerUsername", source = "follower.username")
    FollowerDto toDtoSubscription(FollowerEntity entity);

    /**
     * Так как связь реверсивна, то просто меняем поля местами
     * если хотим получить кто на нас подписан
     * */
    @Mapping(target = "followerId", source = "user.id")
    @Mapping(target = "userId", source = "follower.id")
    @Mapping(target = "followerUsername", source = "follower.username")
    FollowerDto toDtoSubscriber(FollowerEntity entity);


}
