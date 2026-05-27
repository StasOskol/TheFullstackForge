CREATE TABLE IF NOT EXISTS gift_types (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(200),
    icon_url VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS gifts (
    id BIGSERIAL PRIMARY KEY,
    from_user_id BIGINT NOT NULL,
    to_user_id BIGINT NOT NULL,
    gift_type_id BIGINT NOT NULL,
    comment VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_gifts_from_user FOREIGN KEY (from_user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_gifts_to_user FOREIGN KEY (to_user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_gifts_gift_type FOREIGN KEY (gift_type_id) REFERENCES gift_types(id)
);

-- Индексы для быстрых запросов
CREATE INDEX idx_gifts_to_user ON gifts(to_user_id);
CREATE INDEX idx_gifts_from_user ON gifts(from_user_id);
CREATE INDEX idx_gifts_created_at ON gifts(created_at);

-- Добавляем базовые типы подарков
INSERT INTO gift_types (name, description, icon_url) VALUES
('rose', 'Красная роза', '/icons/rose.png'),
('cake', 'Праздничный торт', '/icons/cake.png'),
('bear', 'Плюшевый мишка', '/icons/bear.png'),
('heart', 'Валентинка', '/icons/heart.png'),
('star', 'Звезда', '/icons/star.png'),
('coffee', 'Чашка кофе', '/icons/coffee.png'),
('flower', 'Букет цветов', '/icons/flower.png'),
('chocolate', 'Коробка конфет', '/icons/chocolate.png');
ON CONFLICT (name) DO NOTHING;