-- Создаем пользователя
CREATE TABLE IF NOT EXISTS users (
    id BIGINT GENERATED ALWAYS AS IDENTITY primary key,
    username varchar(50),
    password_hash varchar(100) not null,
    created_at  timestamp(6) without time zone default CURRENT_TIMESTAMP,
    updated_at  timestamp(6) without time zone,
    version INTEGER DEFAULT 0,
    deleted boolean default false
);

create index if not exists users_username_idx ON users(username);