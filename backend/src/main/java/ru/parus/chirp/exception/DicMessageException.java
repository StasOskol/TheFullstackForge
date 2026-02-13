package ru.parus.chirp.exception;

/**
 * DicMessageException
 * <p>
 *     Список сообщений исключений по умолчанию
 * </p>
 *
 * @author Grachev.D.G  zhulvern-92@mail.ru
 */
public interface DicMessageException {
    String CONFLICT_EXCEPTION = "Конфликт данных";
    String NOT_EXIST_EXCEPTION = "Cущности не существует";
    String NOT_EXIST_USER_EXCEPTION = "Пользователя не существует";
    String PERMISSION_DENIED_EXCEPTION = "Недостаточно прав доступа";
    String ACCESS_DENIED_EXCEPTION = "Доступ запрещен";
    String CHAT_BLOCKED_EXCEPTION = "Чат заблокирован";
}
