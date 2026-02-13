-- Таблица подписчиков
CREATE TABLE IF NOT EXISTS followers (
    user_id bigint not null references users(id),
    follower_id bigint not null references users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    primary key (user_id, follower_id),
    check ( user_id != follower_id )
);

-- Создаем только для самого пользователя индекс на подписчиков
create index if not exists followers_user_id_idx ON followers(user_id);
