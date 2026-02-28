package ru.parus.chirp.service;

/**
 * NotificationService
 * <p>
 * </p>
 *
 * @author Grachev.D.G  (zhulvern-92@mail.ru)
 * @version 27.02.2026
 */
public interface NotificationService {
    // Уведомить подписчиков о новом посте
    void notifyAsyncNewPost(Long userId);
}
