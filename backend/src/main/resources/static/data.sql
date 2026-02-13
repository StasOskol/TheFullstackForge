-- Тестовые пользователи
INSERT INTO users (username, email, password_hash, display_name, bio) VALUES
      ('student1', 'student1@college.edu', '{bcrypt}$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTV1VCi', 'Иван Петров', 'Студент 2 курса, учу Java'),
      ('student2', 'student2@college.edu', '{bcrypt}$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTV1VCi', 'Мария Сидорова', 'Люблю Spring Framework'),
      ('teacher', 'teacher@college.edu', '{bcrypt}$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTV1VCi', 'Преподаватель', 'Преподаю веб-разработку');

-- Тестовые чирипы
INSERT INTO posts (user_id, content) VALUES
      (1, 'Первый чирип в системе! #HelloChirper'),
      (2, 'Сегодня изучаю Spring Boot. Очень крутой фреймворк!'),
      (3, 'Напоминаю: дедлайн по проекту Chirper - через 2 недели!'),
      (1, 'Кто-нибудь разобрался с Flyway миграциями?'),
      (2, 'Только что запустил H2 базу - работает отлично!');

-- Подписки
INSERT INTO followers (user_id, follower_id) VALUES
      (1, 2),  -- student1 подписан на student2
      (1, 3),  -- student1 подписан на teacher
      (2, 3);  -- student2 подписан на teacher

-- Лайки
INSERT INTO likes (user_id, chirp_id) VALUES
      (2, 1),  -- student2 лайкнул первый чирип
      (3, 2);  -- teacher лайкнул чирип student2