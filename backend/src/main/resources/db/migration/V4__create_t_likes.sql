-- Таблица лайков
CREATE TABLE IF NOT EXISTS likes (
     user_id BIGINT NOT NULL references users(id) ON DELETE CASCADE,
     post_id BIGINT NOT NULL references posts(id) ON DELETE CASCADE,
     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     PRIMARY KEY (user_id, post_id)
);

CREATE INDEX idx_likes_chirp_id ON likes(post_id);