package ru.parus.chirp.model;

import java.io.Serializable;

/**
 * LikeId
 * <p>
 *     Класс для составного ключа likes
 * </p>
 *
 * @author Grachev.D.G  (zhulvern-92@mail.ru)
 * @version 30.01.2026
 */
public class FollowerId implements Serializable {
    private Long user;
    private Long follower;
}
