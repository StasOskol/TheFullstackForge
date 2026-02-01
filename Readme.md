# The Fullstack Forge
Фулстек-Кузница

> 01.02.2026 (описание работы с db + развёртывание backend)

- [Первый запуск db](#Первый-запуск-db)
- [Вопросы по db](#Вопросы-по-db)

## Database

### Первый запуск db
Скачиваем и устанавливаем Postgresql

Ссылка для скачивания (Windows): <a href="https://postgrespro.ru/windows" target="_blank">PostgreSQL</a>

Обратите внимение на версию PosgresSQL, если не уследили, то информация в [Вопросы по db](#Вопросы-по-db) (см. Вопрос 2)

Далее настраиваем переменную окружения, чтобы работало через `cmd`. Для этого находим ярлык "Мой компьютер" или внитри директории "Мой компютер" нажимаем правой кнопкой -> Свойства -> Дополнительные параметры системы -> Переменные среды -> ищем PATH и нажимаем 2 раза по нему, откроется окно -> Создать -> и вставляем "C:\Program Files\PostgreSQL\18\bin" !НО! 18 меняем на свою версию!

После перезапускаем cmd и вводим команду
```
"C:\Program Files\PostgreSQL\18\bin\psql.exe" -U postgres
```

Если получили ответ:
```
psql (18.0)
ПРЕДУПРЕЖДЕНИЕ: Кодовая страница консоли (866) отличается от основной
                страницы Windows (1251).
                8-битовые (русские) символы могут отображаться некорректно.
                Подробнее об этом смотрите документацию psql, раздел
                "Notes for Windows users".
Введите "help", чтобы получить справку.
```

То всё работает, выход -> `Ctrl + C` или `\q`

СУБД готова к работе, для взаимодействия с ней можно использовать приложения: `pgAdmin 4` или `DBeaver`

Ссылки для скачивания:
* <a href="https://www.pgadmin.org/download/" target="_blank">pgAdmin 4</a>
* <a href="https://dbeaver.io" target="_blank">DBeaver</a>

Я буду использовать `DBeaver`

#### Создание первой database
1. Запускаем `DBeaver` и нажимаем `Новое подключение` -> PostgresSql

2. Заполните параметры подключения:
```text
Главная вкладка:
- Хост (Host): localhost
- Порт (Port): 5432 (стандартный порт PostgreSQL)
- База данных (Database): postgres (или имя вашей базы)
- Имя пользователя (Username): postgres
- Пароль (Password): [пароль, который вы задали при установке]
```

Аналогия для понимания структуры PostgreSQL:

* PostgreSQL — это многоквартирный дом.

* База данных (Database) — квартира, где хранятся ваши вещи (таблицы, данные).

* Пользователь (User) — жилец с ключом от подъезда (доступ к серверу).

* Владелец базы (Owner) — собственник квартиры (может создавать таблицы, управлять правами).

* Гости (Read-only доступ) — могут зайти в квартиру, но ничего не меняют.

3. Создание базы данных (Правой кнопкой по postgress -> Редактор SQL -> Открыть SQL скрипт)
```sql
-- Создаем базу для нашего проекта
CREATE DATABASE the_fullstack_force;
```

Проверяем:
```sql
SELECT datname FROM pg_database WHERE datname = 'the_fullstack_force';
```

4. Создаем пользователей:
```sql
-- 1. Администратор (самый главный)
CREATE USER fs_admin WITH PASSWORD 'admin123';

-- 2. Разработчик (обычный пользователь)
CREATE USER fs_developer WITH PASSWORD 'dev123';

-- 3. Читатель (только смотрит)
CREATE USER fs_viewer WITH PASSWORD 'viewer123';
```

5. Назначаем администратора владельцем:
```sql
ALTER DATABASE the_fullstack_force OWNER TO fs_admin;
```

6. Подключаемся к НОВОЙ базе как администратор
* ВАЖНО! Теперь нужно подключиться к the_fullstack_force, а не к postgres!
    - Создайте новое подключение:
```text
Host: localhost
Port: 5432
Database: the_fullstack_force  ← ИМЯ НАШЕЙ БАЗЫ!
Username: fs_admin
Password: admin123
```

7. Открываем новый SQL скрипт в новом подключении и создаём таблицу:
```sql
create table if not exists products (
	id bigint generated always as identity primary key, -- уникальный номер
	name varchar(100) not null, --название, не может быть пустым
	category varchar(50), -- категория (мобильные, ноутбуки)
	price DECIMAL(10, 2) not null, -- цена (10 цифр всего, 2 после запятой)
	quantity int default 0, -- количество на складе, по умолчанию 0
	created_at timestamp default current_timestamp -- дата обновления
)
```

После создания в панели проекта нажать `F5` для обновления, после этого появится таблица под названием `products`

При создании таблицы обратите внимание на каждую строку, а более подробно на `if not exists` такую строчку нужно добалять везде, так как она говорит о том, что созданий таблицу, если её не существует. Если не прописывать, то можно перезатереть данные таблицы и положить проект

8. Выполняем задание номер 1: `Добавить в таблицу продуктов 3-4 записи`
    - Добавляем одну запись в таблицу
```sql
insert into products (name, category, price, quantity)
values ('iPhone 15', 'Смартфон', 999.99, 10)
```
    - Добавляем стек товаров (несколько товаров за раз)
```sql
insert into products (name, category, price, quantity) values
('MacBook Air M2', 'Ноутбуки', 1299.99, 5),
('Samsung Galaxy S24', 'Смартфоны', 849.99, 15),
('Наушники Sony', 'Аксессуары', 199.99, 30);
```

Проверка результата:
```sql
select * from products;
```

Данные должны полностью отобразиться, обратите внимение на время создания, когда добавляли несколько данных за раз, время создания одинаковое у всех 3-х товаров

Запрос на проверку опасный, так как если записей в таблице миллионы, то положим базу данных, лучше использовать:
```sql
select * from products limit 2;
```

или если нужно посмотреть последние записи, то:
```sql
SELECT * FROM products 
ORDER BY created_at DESC 
LIMIT 2;
```

Но эти команды всё равно не безопасные, лучше использовать `explain` или `explain analize`

9. Пример использования команды `explain`
```sql
explain select * from products
```
EXPLAIN - только планирование

Вывод (пример):
```text
                       QUERY PLAN
---------------------------------------------------------
 Seq Scan on products  (cost=0.00..15.00 rows=1000 width=36)
```

Что показывает:

* cost=0.00..15.00 — оценка стоимости (условные единицы)

* 0.00 — стоимость запуска

* 15.00 — общая стоимость

* rows=1000 — ожидаемое количество строк

* width=36 — средний размер строки в байтах
---

10. Пример использования команды `explain analyze`
```sql
explain analyze select * from products
```

Вывод (пример):
```text
                       QUERY PLAN
---------------------------------------------------------
 Seq Scan on products  (cost=0.00..15.00 rows=1000 width=36)
                       (actual time=0.008..0.250 rows=1000 loops=1)
 Planning Time: 0.050 ms
 Execution Time: 0.300 ms
```

Что показывает дополнительно:

* actual time=0.008..0.250 — реальное время в миллисекундах

* 0.008 — время до первой строки

* 0.250 — время получения всех строк

* rows=1000 — реальное количество строк

* loops=1 — сколько раз выполнялся этот шаг

* Planning Time — время на построение плана

* Execution Time — общее время выполнения

11. Когда что использовать
* Используйте EXPLAIN когда:
    - Хотите быстро посмотреть план запроса

    - Работаете с данными в продакшене (безопасно)

    - Анализируете сложный запрос без его выполнения

    - Учитесь читать планы выполнения

* Используйте EXPLAIN ANALYZE когда:
    - Оптимизируете медленный запрос

    - Тестируете на тестовых данных

    - Хотите сравнить разные варианты запроса

    - Нужны реальные метрики (время, строки)

12. Подключаем других пользователей к базе и настраиваем права
```sql
-- 1. Даем права на подключение к базе для всех пользователей
GRANT CONNECT ON DATABASE the_fullstack_force TO fs_developer, fs_viewer;
```

```sql
-- 2. Даем права на использование схемы public (или созданной вами схемы)
GRANT USAGE ON SCHEMA public TO fs_developer, fs_viewer;
```

```sql
-- Разработчик: может читать, добавлять, изменять, удалять данные
GRANT SELECT, INSERT, UPDATE, DELETE ON products TO fs_developer;
```

```sql
-- Читатель: может только читать данные
GRANT SELECT ON products TO fs_viewer;
```

13. Создаем схему для лучшей организации (опционально, но рекомендуется)
```sql
-- Создаем отдельную схему для нашего приложения
CREATE SCHEMA IF NOT EXISTS app_schema;

-- Переносим таблицу в схему
ALTER TABLE products SET SCHEMA app_schema;

-- Даем права на схему
GRANT USAGE ON SCHEMA app_schema TO fs_developer, fs_viewer;

-- Обновляем права на таблицу в новой схеме
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA app_schema TO fs_developer;
GRANT SELECT ON ALL TABLES IN SCHEMA app_schema TO fs_viewer;
```

14. Права на будущие таблицы в схеме app_schema
```sql
-- Автоматически давать права на новые таблицы в схеме
ALTER DEFAULT PRIVILEGES IN SCHEMA app_schema
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO fs_developer;

ALTER DEFAULT PRIVILEGES IN SCHEMA app_schema
GRANT SELECT ON TABLES TO fs_viewer;

-- Для последовательностей
ALTER DEFAULT PRIVILEGES IN SCHEMA app_schema
GRANT USAGE, SELECT ON SEQUENCES TO fs_developer;
```

15. Тестируем права разных пользователей

fs_developer (должен читать и изменять)

* Создайте новое подключение в DBeaver:
```text
Database: the_fullstack_force
Username: fs_developer
Password: dev123
```

* Выполните запросы по очередно и проанализируйте:
```sql
SELECT * FROM app_schema.products; -- Чтение ✓

INSERT INTO app_schema.products (name, category, price, quantity)
VALUES ('Новый товар', 'Тест', 100.00, 5); -- Вставка ✓

UPDATE app_schema.products 
SET price = price * 1.1 
WHERE category = 'Смартфоны'; -- Обновление ✓

SELECT * FROM app_schema.products; -- Чтение ✓

DELETE FROM app_schema.products 
WHERE id = 5; -- Удаление ✓

SELECT * FROM app_schema.products; -- Чтение ✓

CREATE TABLE app_schema.test (id SERIAL); -- Создание таблицы ✗

DROP TABLE app_schema.products; -- Удаление таблицы ✗
```
---

fs_viewer (только чтение)

* Создайте новое подключение в DBeaver:
```text
Database: the_fullstack_force
Username: fs_viewer
Password: viewer123
```

* Выполните запросы по очередно и проанализируйте:
```sql
-- ДОЛЖНО РАБОТАТЬ:
SELECT * FROM app_schema.products;  -- Чтение ✓
SELECT COUNT(*) FROM app_schema.products;  -- Агрегация ✓

-- НЕ ДОЛЖНО РАБОТАТЬ (ошибка):
INSERT INTO app_schema.products (name, category, price, quantity)
VALUES ('Запрещено', 'Тест', 50.00, 1);  -- Вставка ✗

UPDATE app_schema.products SET price = 0;  -- Обновление ✗
DELETE FROM app_schema.products; 
```

16. Создаем дополнительные таблицы с правами
Вернитесь в подключение как fs_admin и создайте еще таблицу:
```sql
-- Таблица заказов
CREATE TABLE IF NOT EXISTS app_schema.orders (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    product_id BIGINT REFERENCES app_schema.products(id),
    user_id BIGINT,
    quantity INTEGER NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL,
    status VARCHAR(20) DEFAULT 'pending',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

Права автоматически применятся благодаря ALTER DEFAULT PRIVILEGES

16. Резюме созданной структуры
```text
┌─────────────────────────────────────────────────────────┐
│               База: the_fullstack_force                 │
│                    Владелец: fs_admin                   │
├─────────────────────────────────────────────────────────┤
│                                                         │
│  fs_admin       →  ВСЁ: SELECT, INSERT, UPDATE,         │
│                     DELETE, CREATE, DROP, ALTER         │
│                                                         │
│  fs_developer   →  Данные: SELECT, INSERT, UPDATE,      │
│                     DELETE (но не структуру)            │
│                                                         │
│  fs_viewer      →  Только: SELECT                       │
│                                                         │
│  fs_backup      →  Только: SELECT (для бэкапов)         │
│                                                         │
└─────────────────────────────────────────────────────────┘
```

17. Удалите ненужную таблицу:
```sql
DROP TABLE app_schema.orders;
```

F5

#### Выполнение задачи №3  создать новую таблицу категорий продуктов (связь с products Many-to-Many)
Связать таблицу продуктов с таблицей категорий в отношении многие ко многим

Запросы осуществлять от имени админа в схеме app_schema -> sql-запрос
1. Создаём таблицу категорий
```sql
-- Таблица категорий продуктов
CREATE TABLE IF NOT EXISTS app_schema.categories (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,           -- уникальное название категории
    slug VARCHAR(50) UNIQUE NOT NULL,           -- URL-дружественное имя (например: smartphones)
    description TEXT,                           -- описание категории
    parent_id BIGINT REFERENCES app_schema.categories(id), -- иерархия (подкатегории)
    is_active BOOLEAN DEFAULT true,             -- активна ли категория
    sort_order INTEGER DEFAULT 0,               -- порядок сортировки
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

```sql
-- Комментарий к таблице
COMMENT ON TABLE app_schema.categories IS 'Таблица категорий товаров';
COMMENT ON COLUMN app_schema.categories.slug IS 'URL-дружественное название (латиница, без пробелов)';
COMMENT ON COLUMN app_schema.categories.parent_id IS 'ID родительской категории (NULL для корневых)';
```

2. Создаём таблицу связки
Создаем таблицу-связку для отношения многие-ко-многим
```sql
CREATE TABLE IF NOT EXISTS app_schema.product_categories (
    product_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    is_primary BOOLEAN DEFAULT false,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (product_id, category_id)
);
```

```sql
COMMENT ON TABLE app_schema.product_categories IS 'Связь продуктов и категорий (many-to-many)';
COMMENT ON COLUMN app_schema.product_categories.is_primary IS 'Является ли основной категорией для продукта';
```

3. Обновляем зависимости и данные
```sql
-- Сначала создаем резервную копию данных
CREATE TABLE IF NOT EXISTS app_schema.products_backup AS 
SELECT * FROM app_schema.products;

-- Удаляем старое поле category (если оно есть)
ALTER TABLE app_schema.products 
DROP COLUMN IF EXISTS category;

-- Добавляем поле для основной категории (опционально, если нужно быстро получать)
ALTER TABLE app_schema.products 
ADD COLUMN IF NOT EXISTS primary_category_id BIGINT REFERENCES app_schema.categories(id);

-- Комментарий
COMMENT ON COLUMN app_schema.products.primary_category_id IS 'ID основной категории (дублирование для оптимизации)';
```

4. Заполняем данным таблицы
* Категории:

```sql
INSERT INTO app_schema.categories (name, slug, description) VALUES
('Смартфоны', 'smartphones', 'Мобильные телефоны и смартфоны'),
('Ноутбуки', 'laptops', 'Переносные компьютеры'),
('Аксессуары', 'accessories', 'Аксессуары для техники'),
('Наушники', 'headphones', 'Наушники и гарнитуры'),
('Планшеты', 'tablets', 'Планшетные компьютеры'),
('Игровые', 'gaming', 'Игровые устройства и аксессуары'),
('Аудиотехника', 'audio', 'Аудио оборудование'),
('Фотокамеры', 'cameras', 'Фото и видео камеры');
```

* Подкатегории:
```sql
-- Создаем подкатегории
INSERT INTO app_schema.categories (name, slug, description, parent_id) VALUES
('Apple iPhone', 'apple-iphone', 'Смартфоны Apple', 1),
('Android', 'android', 'Смартфоны на Android', 1),
('Игровые ноутбуки', 'gaming-laptops', 'Ноутбуки для игр', 2),
('Беспроводные наушники', 'wireless-headphones', 'Наушники Bluetooth', 4),
('Проводные наушники', 'wired-headphones', 'Наушники с проводом', 4);
```

* Связываем существующие продукты с категориями
```sql
-- 1. Сначала свяжем iPhone 15 с категорией "Apple iPhone"
INSERT INTO app_schema.product_categories (product_id, category_id, is_primary)
SELECT 
    p.id as product_id,
    c.id as category_id,
    true as is_primary
FROM app_schema.products p
CROSS JOIN app_schema.categories c
WHERE p.name = 'iPhone 15' AND c.slug = 'apple-iphone';

-- 2. Свяжем MacBook Air с категорией "Ноутбуки"
INSERT INTO app_schema.product_categories (product_id, category_id, is_primary)
SELECT 
    p.id as product_id,
    c.id as category_id,
    true as is_primary
FROM app_schema.products p
CROSS JOIN app_schema.categories c
WHERE p.name = 'MacBook Air M2' AND c.slug = 'laptops';

-- 3. Свяжем Samsung Galaxy с категориями "Android" и "Смартфоны"
INSERT INTO app_schema.product_categories (product_id, category_id, is_primary)
VALUES
((SELECT id FROM app_schema.products WHERE name = 'Samsung Galaxy S24'),
 (SELECT id FROM app_schema.categories WHERE slug = 'android'), true),
((SELECT id FROM app_schema.products WHERE name = 'Samsung Galaxy S24'),
 (SELECT id FROM app_schema.categories WHERE slug = 'smartphones'), false);

-- 4. Наушники Sony свяжем с несколькими категориями
INSERT INTO app_schema.product_categories (product_id, category_id, is_primary)
VALUES
((SELECT id FROM app_schema.products WHERE name = 'Наушники Sony'),
 (SELECT id FROM app_schema.categories WHERE slug = 'accessories'), false),
((SELECT id FROM app_schema.products WHERE name = 'Наушники Sony'),
 (SELECT id FROM app_schema.categories WHERE slug = 'headphones'), true),
((SELECT id FROM app_schema.products WHERE name = 'Наушники Sony'),
 (SELECT id FROM app_schema.categories WHERE slug = 'audio'), false);
```

* Обновляем поле primary_category_id в products
```sql
-- Обновляем products.primary_category_id на основе связей
UPDATE app_schema.products p
SET primary_category_id = pc.category_id
FROM app_schema.product_categories pc
WHERE p.id = pc.product_id 
  AND pc.is_primary = true;
```

* Тестовые запросы для проверки связи
```sql
-- 1. Все продукты с их категориями
SELECT 
    p.id,
    p.name as product_name,
    p.price,
    STRING_AGG(c.name, ', ') as categories,
    MAX(CASE WHEN pc.is_primary THEN c.name END) as primary_category
FROM app_schema.products p
LEFT JOIN app_schema.product_categories pc ON p.id = pc.product_id
LEFT JOIN app_schema.categories c ON pc.category_id = c.id
GROUP BY p.id, p.name, p.price
ORDER BY p.id;

-- 2. Все категории с количеством товаров
SELECT 
    c.id,
    c.name as category_name,
    c.slug,
    COUNT(DISTINCT pc.product_id) as product_count
FROM app_schema.categories c
LEFT JOIN app_schema.product_categories pc ON c.id = pc.category_id
GROUP BY c.id, c.name, c.slug
ORDER BY product_count DESC;

-- 3. Продукты в конкретной категории
SELECT 
    p.id,
    p.name,
    p.price,
    p.quantity
FROM app_schema.products p
JOIN app_schema.product_categories pc ON p.id = pc.product_id
JOIN app_schema.categories c ON pc.category_id = c.id
WHERE c.slug = 'smartphones'
ORDER BY p.price DESC;

-- 4. Иерархия категорий (родитель → дети)
SELECT 
    parent.name as parent_category,
    child.name as child_category,
    child.slug
FROM app_schema.categories parent
LEFT JOIN app_schema.categories child ON child.parent_id = parent.id
WHERE parent.parent_id IS NULL  -- только корневые категории
ORDER BY parent.name, child.name;
```

* Создаем индексы для оптимизации
```sql
-- Индексы для таблицы categories
CREATE INDEX IF NOT EXISTS idx_categories_parent_id ON app_schema.categories(parent_id);
CREATE INDEX IF NOT EXISTS idx_categories_slug ON app_schema.categories(slug);
CREATE INDEX IF NOT EXISTS idx_categories_is_active ON app_schema.categories(is_active);

-- Индексы для таблицы product_categories
CREATE INDEX IF NOT EXISTS idx_product_categories_category_id ON app_schema.product_categories(category_id);
CREATE INDEX IF NOT EXISTS idx_product_categories_product_id ON app_schema.product_categories(product_id);
CREATE INDEX IF NOT EXISTS idx_product_categories_is_primary ON app_schema.product_categories(is_primary);

-- Индекс для products (если нужно искать по категории)
CREATE INDEX IF NOT EXISTS idx_products_primary_category ON app_schema.products(primary_category_id);
```

* Триггер для автоматического обновления updated_at
```sql
-- Функция для обновления времени
CREATE OR REPLACE FUNCTION app_schema.update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

-- Триггер для таблицы categories
CREATE TRIGGER update_categories_updated_at
    BEFORE UPDATE ON app_schema.categories
    FOR EACH ROW
    EXECUTE FUNCTION app_schema.update_updated_at_column();

-- Триггер для таблицы products
CREATE TRIGGER update_products_updated_at
    BEFORE UPDATE ON app_schema.products
    FOR EACH ROW
    EXECUTE FUNCTION app_schema.update_updated_at_column();
```

* Представления (Views) для удобства
```sql
-- Представление: продукты с основной категорией
CREATE OR REPLACE VIEW app_schema.products_with_primary_category AS
SELECT 
    p.*,
    c.name as primary_category_name,
    c.slug as primary_category_slug
FROM app_schema.products p
LEFT JOIN app_schema.categories c ON p.primary_category_id = c.id;

-- Представление: все связи продуктов и категорий
CREATE OR REPLACE VIEW app_schema.product_category_details AS
SELECT 
    p.id as product_id,
    p.name as product_name,
    c.id as category_id,
    c.name as category_name,
    c.slug as category_slug,
    pc.is_primary,
    pc.created_at as linked_at
FROM app_schema.products p
JOIN app_schema.product_categories pc ON p.id = pc.product_id
JOIN app_schema.categories c ON pc.category_id = c.id;

-- Проверяем представления
SELECT * FROM app_schema.products_with_primary_category LIMIT 5;
SELECT * FROM app_schema.product_category_details LIMIT 5;
```

#### Вопросы по db
1. Почему не MySQL, а postgresSQL?
    - PostgresSQL "жёстко" тоже самое, что и MySQL, но с дополнениями и пакетами, которые улучшают (увеличенная скорость, более надёжная и безопасная)

2. Как узнать какая версия PosgresSQL установлена?
    - В windiows перейдите в "C:\Program Files\PostgreSQL\" и увидите версию, которая стоит.

3. Как проверить работает ли СУБД PostgresSQL
    - В `cmd` ввести команду
```
"C:\Program Files\PostgreSQL\18\bin\psql.exe" -U postgres
```

!Внимание! 18 - версия PostgresSQL, нужно ввести свою

Если ответ:
```
psql (18.0)
ПРЕДУПРЕЖДЕНИЕ: Кодовая страница консоли (866) отличается от основной
                страницы Windows (1251).
                8-битовые (русские) символы могут отображаться некорректно.
                Подробнее об этом смотрите документацию psql, раздел
                "Notes for Windows users".
Введите "help", чтобы получить справку.
```

то всё работает, выход -> `Ctrl + C` или `\q`

4. Что делать, если ошиблись в название таблицы?
    - команда, чтобы переименовать таблицу:
```sql
ALTER TABLE старое_имя RENAME TO новое_имя;
```