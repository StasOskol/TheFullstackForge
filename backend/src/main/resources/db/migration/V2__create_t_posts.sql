-- Создаем таблицу постов
CREATE TABLE IF NOT EXISTS posts (
    id BIGINT GENERATED ALWAYS AS IDENTITY primary key,
    user_id bigint references users(id),
    content varchar(120) not null,
    created_at  timestamp(6) without time zone default CURRENT_TIMESTAMP,
    updated_at  timestamp(6) without time zone,
    version INTEGER DEFAULT 0,
    deleted boolean default false
);

create index if not exists posts_user_id_idx ON posts(user_id);