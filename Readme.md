# The Fullstack Forge

Фулстек-Кузница

> 04.04.2026 (описание работы с db + развёртывание backend + frontend)

- [](#)

---

# Запуск приложений

> frontend

```
cd frontend
npm i
npm run dev
```

> backend
> Запуск с очисткой

```
cd backend
mvn clean spring-boot:run
```

Простой запуск

```
mvn spring-boot:run
```

---

# Анализ файлов backend и полный разбор

1. Самый важный файл `pom.xml`
   В нём описаны все зависимости, библиотеки используемые в проекте

2. Файл `backend => src => main => resources => application.yml`
   Настройки подключения к базе данных.

```yml

...
datasource:
  url: ${JDBC_URL:jdbc:postgresql://localhost:5432/twit_ru} // здесь описывается куда мы, к какой базе мы подключаемся, но она должны быть создана (например через dbeaver)
  username: ${JDBC_USER:fs_admin} // логин для подключения к данной базе (в примере twit_ru)
  password: ${JDBC_PASSWORD:admin123} // пароль для подключения к данной базе (в примере twit_ru)
  driver-class-name: org.postgresql.Driver
  dbcp2:
    default-schema: public
...
flyway:
  locations: classpath:/db/migration
  driver-class-name: org.postgresql.Driver
  user: ${JDBC_USER:fs_admin} // логин для подключения к данной базе (в примере twit_ru)
  password: ${JDBC_PASSWORD:admin123} // пароль для подключения к данной базе (в примере twit_ru)
  enabled: true
  default-schema: public
...
```

Это не весь файл, а показаны только строчки, которые важны на данный момент

3. Файлы миграции `backend => src => main => resources => db => migration => файлы`
   Обратите внимение как названы файлы: `V1__create_t_users.sql` => `V1` - версия изменения базы данных, `__` - два нижных подчёркивания, далее описывается, что будет изменять базу, в нашем случае `создание`, то есть `create`, нижнее подчёркивание, что создаём, `t` - таблица, (если нужно в базе создать триггер, то `tg`), ну и название самой таблицы, то есть что будет создаваться в самой базе (таблица под названием users) `users`

В папке `migration` есть ещё файлы, которые позволяют создавать, удалять и изменять базу в процессе приложения, удалять `V` нельзя или придётся сносить базу, но если база уже в продакшн, то потеряем данные.

Разберём один из фалов:

```sql
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
```

Уже знакомый синтаксис, создание таблицы `users` c полями (обратите внимание, что `if not exist` важен):

- id
- username
- password_hash
- created_at
- updated_at
- version
- deleted

и создание index для быстрого поиска нужного user по `username`

Посмотрите содержимое других файлов и проанализируйте их.

То есть, если Вам понадобится в проекте создать таблицу, триггер, функцию в `db`, то нужно создать следующий файлик, в названии описать, что он делает в `db` и написать код `SQL`.

Если вы удалите файлик, или попытаетесь изменить содержимое, после запуска в готовую `db`, то у вас будет расходится хеш-сумма, что приведёт к потере данный.

4. Рассмотрим файлы `backend -> src -> main -> java -> ru -> parus -> chirp`:

- `-> config`
  В этой папке настройки проекта, обратите внимание на файл `SecurityConfig.java`:

```java
...
.authorizeHttpRequests(auth -> auth
  .requestMatchers(
    "/swagger-ui/**",
    "/v3/api-docs/**",
    "/api/v1/auth/**",
    "/error",
    "/actuator/health"
  ).permitAll()
  .anyRequest().authenticated()
)
...
```

В этих строках заложены `router`, которые доступны без `token` ключа. Все остальные контроллеры работают только с ключом.

Так как мы в предыдущем рассматривали создание `post`, то рассмотрим для примера контроллер для `posts` и другие файлы для работы с базой и системой.

- `-> model`
Первое, что создаётся для работы с таблицей - это модель данных с полями.

Рассматриваем файл `-> dto -> post PostDto.java`:
```java
package ru.parus.chirp.model.dto.post; // Подключение к проекту (как експорт, чтобы другие файлы знали, где находится файл PostDto)

import java.io.Serializable; // Преобразование файла с полями в байты для передачи по сети http-сессиях
import lombok.Data; // библиотека для генерации геттеров и сеттеров автоматически

@Data // Data - от lombok: автоматическая генерация геттеров и сеттеров
public class PostDto implements Serializable { // Создание класса с Serializable
    private String content; // Поля из таблицы Post с типами
    private Long userId;
}
```

Так нужно описывать поля, которые находятся в таблице. Берём из `db`.

Создаём следующий файл `PostEntity.java` (в нашем случае мы просто рассмотрим, так как он уже создан и в проекте существует):
```java
package ru.parus.chirp.model; // эксорт, чтобы другие файлы видели PostEntity.java

import jakarta.persistence.Column; // jakarta - связь таблиц с полями из db
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter; // библиотека для автоматического написания get
import lombok.Setter; // -||- set

@Getter // Автоматическое создание get
@Setter // Автоматическое создание set
@Entity // Это строка говорит что всё описанное в классе соответсвует таблице в БД
@Table(name = "posts") // Таблица в базе с названием posts в БД
public class PostEntity extends BaseEntity { // Класс PostEntity, который насоедует поля из класса BaseEntity. В классе BaseEntity есть поля, которые должны быть у всех таблиц по умолчанию, это поля даты создания записи, даты измения и другие, то есть файл BaseEntity нужен по умолчанию
    @Id // Поле id в таблице posts. Это не простой id, а первичный ключ, используемый для поиска
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Эта строка говорит о том, что при создании нового поста поле id генерируется автоматически, полагается на автоинкремент в связи с БД (автоинкремент + 1)
    private Long id; // Само поле id  в таблице

    @ManyToOne(fetch = FetchType.LAZY) // Описание поля - связь один ко многим, то есть постов может быть много, а автор поста только один. fetch - ленивая загрузка, то есть данные не загружаются сразу из БД, а только когда вызовется post.getOwner()
    @JoinColumn(name = "user_id") // Поле для внешнего ключа, в нашем случае user_id, чтобы быстро находить все записи у данного пользователя. Эта колонка будет ссылаться на таблицу user и из неё брать id
    private UserEntity owner; // Само поле owner, ссылка на объект пользователя

    @Column(name = "content", nullable = false) // Колонка в таблице под названием content, не может быть пустым
    private String content; // Само поле в приложении
}
```

Этот файл связывает таблицу `db` с полями `backend`-приложения.

- `-> repository`
Следующий файл для создания (рассмотрения) `PostRepository.java`

Самый важный файл, для того чтобы подключить все сущности и соотнести с БД.

```java
package ru.parus.chirp.repository; // Экспорт файла

import org.springframework.data.jpa.repository.JpaRepository; // Подключение CRUD-операции, пагинацию, сортировку
import ru.parus.chirp.model.PostEntity; // Подключаем модель PostEntity

public interface PostRepository extends JpaRepository<PostEntity, Long> { // Описание действий с базой
}
```

Подключение методов create, update, delete и других методов взаимодействия с БД

- `-> mapper`: 

Зачем нужен Mapper (маппер)?

В любом приложении есть два представления данных:
```java
// Entity - для базы данных (внутреннее)
PostEntity {
    private Long id;
    private UserEntity owner;  // ← целый объект!
    private String content;
}

// DTO - для клиента (внешнее)
PostDto {
    private String content;
    private Long userId;       // ← только ID пользователя
}
```

Как превратить одно в другое?

Ручное копирование (плохо)
* Минусы: много повторяющегося кода, легко ошибиться, трудно поддерживать

Решение:
```java
// Один раз описали правила:
@Mapping(target = "userId", source = "owner.id")
PostDto toDto(PostEntity entity);

// И используем везде:
PostDto dto = postMapper.toDto(entity);  // готово!
```

Разберём строки кода файла `PostMapper.java`:
```java
package ru.parus.chirp.mapper; // экспорт

import org.mapstruct.BeanMapping; // аннотации MapStruct для описания правил преобразования
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.parus.chirp.model.PostEntity; // Преобразование данных из одного в другой
import ru.parus.chirp.model.dto.post.PostDto;

@Mapper(componentModel = "spring") // Подключение Mapper
public interface PostMapper { // Создаём интерфейс

    @Mapping(target = "userId", source = "owner.id") // превращает Entity (из БД) в DTO (для клиента). В Entity нет поля userId, есть owner.id. @Mapping объясняет связь: target = "userId" → поле в DTO source = "owner.id" откуда взять значение
    PostDto toDto(PostEntity entity); // Преобразует данные из БД и преврати их в формат для клиента (DTO)

    PostEntity toEntity(PostDto dto); // данные от клиента (DTO) -> сохрани в БД (Entity)

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE) // Анатация, что делать (объясняем приложению, что делать), если значение null, ничего нет, то игнорировать поле, не трогать entity-данные в базе
    void patchUpdate(PostDto dto, @MappingTarget PostEntity entity); // Частичное обновление данных. Принимает данные от клиента с полями PostDto -> преобразуем PostEntity в БД через mapper
}
```

Аналогия: переводчик с русского на английский
Представьте, что:

* PostEntity = русский язык (внутренний, для базы данных)
* PostDto = английский язык (внешний, для клиента/браузера)
* Маппер = переводчик, который умеет:

1. Переводить с русского на английский (toDto)
2. Переводить с английского на русский (toEntity)
3. Исправлять текст в уже существующем документе (patchUpdate)

- `-> services`:
Бизнес логика и описание методов

Если соотносить с языком `c++`, что если функции находятся ниже функции main, то нужно создавать `прототип`.

Тут тоже самое, если мы хотим описать функции, что они делают, то нужно сначала описать, что за функции и что возвращают.

Начнём с написанием самой логики:
файл `-> impl -> PostServiceImpl.java`:
```java
package ru.parus.chirp.service.impl; // экспорт

import lombok.RequiredArgsConstructor; // Логирование (ошибки)
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page; // Пагинация
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.parus.chirp.exception.NotExistException; // Подключение контрукторов из других файлов
import ru.parus.chirp.exception.PermissionDeniedException;
import ru.parus.chirp.mapper.PostMapper;
import ru.parus.chirp.model.PostEntity;
import ru.parus.chirp.model.UserEntity;
import ru.parus.chirp.model.dto.post.PostDto;
import ru.parus.chirp.repository.PostRepository;
import ru.parus.chirp.service.NotificationService;
import ru.parus.chirp.service.PostService;
import ru.parus.chirp.service.UserService;

@Slf4j // для логирования
@Service // регистрируем как Spring бин
@RequiredArgsConstructor // конструктор для final полей
public class PostServiceImpl implements PostService { // PostServiceImpl наследуется от прототипа в PostService, будет описан позже

    // Создаём переменные в которых лежат зависимости из других файлов, final - говорит о том, что мы не можем их в процессе менять
    private final PostRepository postRepository; // работа с БД
    private final PostMapper postMapper; // конвертация Entity ↔ DTO
    private final UserService userService; // получение текущего пользователя
    private final NotificationService notificationService; // отправка уведомлений

    @Override // Бизнес логика для создания поста
    @Transactional // "Всё, что внутри метода, делай в одной транзакции" Если что-то пойдёт не так → всё откатится (ROLLBACK)
    public PostDto create(PostDto dto) { // Функция create, которая возвращет PostDto, и принимает dto
        UserEntity user = userService.getCurrentUserEntity(); // Создаём user на основе UserEntity
        PostEntity postEntity = postMapper.toEntity(dto); // Создаём объект с полями постов, через mapper преобразуем входящие данные в PostEntity через mapper
        postEntity.setOwner(user); // установи в объекте postEntity поле owner - текущего пользователя
        var result = postMapper.toDto(postRepository.save(postEntity)); // Сохрани в БД postEntity и данные которые сохранены преобразуй через mapper в Dto и помести в переменную result
        notificationService.notifyAsyncNewPost(user.getId()); // Не ждем завершения!, и отправляем уведомление
        return result; // Возвращаем данные
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostDto> index(final Pageable pageable) {
        Page<PostEntity> pageEntities;
        pageEntities = postRepository.findAll(pageable);
        return new PageImpl<>(pageEntities.getContent().stream().map(postMapper::toDto).toList(),
                        pageable,
                        pageEntities.getContent().size()
                );
    }

    @Override
    @Transactional(readOnly = true)
    public PostDto show(Long id) {
        PostEntity post = postRepository.findById(id)
                .orElseThrow(NotExistException::new);
        return postMapper.toDto(post);
    }

    @Override
    @Transactional
    public PostDto update(Long id, PostDto dto) {
        UserEntity user = userService.getCurrentUserEntity();
        PostEntity post = postRepository.findById(id).orElseThrow(NotExistException::new);
        if (post.getOwner().getId().equals(user.getId())) {
            postMapper.patchUpdate(dto, post);
            postRepository.save(post);
            return postMapper.toDto(post);
        }
        throw new PermissionDeniedException();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        UserEntity user = userService.getCurrentUserEntity();
        PostEntity post = postRepository.findById(id)
                .orElseThrow(NotExistException::new);
        if (post.getOwner().getId().equals(user.getId())) {
            postRepository.delete(post);
        }
        throw new PermissionDeniedException();
    }
}
```

- `-> controller`:
  Папка с контроллерами. Это ссылки для связи с `backend` сервисом. В проекте есть встроенный плагин `swagger`, который подсказывает, что за ссылка, какие входные и выходные данные.

```java
package ru.parus.chirp.controller; // REST-контроллеры приложения

import io.swagger.v3.oas.annotations.Operation; // библиотека swagger с автоматической генерацией документации
import io.swagger.v3.oas.annotations.responses.ApiResponse; // библиотека swagger
import io.swagger.v3.oas.annotations.responses.ApiResponses; // библиотека swagger
import lombok.RequiredArgsConstructor; // создаёт конструктор для final полей (компиляция и дополнения нужных полей)
import lombok.extern.slf4j.Slf4j; // добавляет поле log для логирования
import org.springframework.data.domain.Page; // Spring-аннотации для REST-эндпоинтов Пагинация
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.parus.chirp.model.dto.post.PostDto; // Dto - данные которые должны получать или передаваться (поля)
import ru.parus.chirp.service.PostService;

@Slf4j // Логирование. Реакция на ошибки и вывод в консоль
@RestController // Это контроллер (get, post, put, patch, delete) - c возвращением json-данных
@RequestMapping(value = "/posts", produces = MediaType.APPLICATION_JSON_VALUE) // Базовый router для работы с постами. (produces - ответ json-данными)
@RequiredArgsConstructor // Автоматическая генерация полей, нужных для компиляции
public class PostController { // Класс для работы с Post

    private final PostService postService; // Конструктор, которые описывает модель (какие поля и что делает)

    @PostMapping("/") // Если продолжение route (post - создание объекта внутри таблицы)
    @Operation(summary = "Создание поста", // Описание router
            description = "Создает пост только для авторизованного пользователя")
    @ApiResponses(value = { // В случае успешного ответа
            @ApiResponse(responseCode = "200", description = "Успешный ответ"),
    })
    public ResponseEntity<PostDto> create(@RequestBody PostDto dto) { // публичная api, принимающее PostDto с полями (String content; private Long userId), описаны поля в PostDto. create - создание нового объекта с этими же полями
        return ResponseEntity.ok(postService.create(dto)); // Вернуть статус ok с объектом созданным
    }

    @GetMapping("/") // GetMapping - просмотр объектов с элементами пагинации
    @Operation(summary = "Просмотр постов пользователя", // Описание api
            description = "")
    @ApiResponses(value = { // В случае успешного ответа
            @ApiResponse(responseCode = "200", description = "Успешный ответ"),
    })
    public ResponseEntity<Page<PostDto>> index(@PageableDefault Pageable pageable) { // Возвращает страницы с PostDto c пагинацией поиск по индексу
        return ResponseEntity.ok(postService.index(pageable));
    }

    @GetMapping("/{id}") // Просмотр по id
    @Operation(summary = "Просмотр поста пользователя", // Описание
            description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный ответ"),
    })
    public ResponseEntity<PostDto> show(@PathVariable Long id) { // show возвра Dto с поисеом по id
        return ResponseEntity.ok(postService.show(id)); // Возврат самого поста
    }

    @PatchMapping("/{id}") // Patch - изменение post
    @Operation(summary = "Обновление поста пользователя",
            description = "Требуется авторизация")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный ответ"),
    })
    public ResponseEntity<PostDto> update(@PathVariable Long id, @RequestBody PostDto dto) { // update, изменение, принимает id post для изменения + тело изменения post dto
        return ResponseEntity.ok(postService.update(id, dto));
    }

    @DeleteMapping("/{id}") // Detele post
    @Operation(summary = "Удаление поста пользователя",
            description = "Требуется авторизация")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный ответ"),
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) { // Возвращает ничего, только статус Void, по id - принимает
        postService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
```

# Вопросы по fullstack

1. Где смотреть состояние запроса?
   Вкладка для отладки в браузере на стороне frontend: Сеть или Network, возможен исход блокировки запроса CORS.

2. Как обойти CORS?
   Если сработал CORS, это блокирока, чтобы не отрабатывали сторонние скрипты. Если ваш backend был на отдельном сервере (реальном), то CORS бы не срабатывал и любой браузер его открывал. Но для разработки нам нужно обойти CORS.

- Для этого скачиваем браузер Google Chrome
- Создаём ярлык на рабочем столе и переименовываем этот ярлык `Google Chrome NO CORS`, чтобы понимать что защита отключена в данном ярлыке
- Далее правой кнопкой по ярлыку -> свойства -> ищем поле `Объект`
- ДОБАВЛЯЕМ к написанному строку (через пробел): `--disable-web-security --user-data-dir="C:\Program Files\Google\Chrome\Application\chrome.exe"`
- Открываем браузер через этот ярлык, сверху должна появится надпись: `Вы используете неподдерживаемый флаг командной строки: --disable-web-security. Стабильность и безопасность будут нарушены.`
- Если эта надпись появилась, значит CORS отключён и запросы будут проходить.

3. Где посмотреть token, сохранён ли он вообще?
   На вкладке браузера `Application` в разделе `Storage` -> `local storage` -> `http://localhost:3000` справа вы будете наблюдать token ключ, он будет применяться, чтобы другие api срабатывали

4. Как очистить данные локального хранилища (local starage)?
   В браузере на панели разработчика заходим в `Console` и пишем команду:

```bash
localStorage.clear()
```

5. Для чего `application-local.yml`?
   Для настроек локального запуска проекта, но не понятно как запускать именно этот файл

6. Какой правильный порядок создания backend-файлов?
```
src/main/java/ru/parus/chirp/
│
├── model/
│   ├── PostEntity.java           (1. Сущность БД)
│   └── dto/post/
│       └── PostDto.java          (1. DTO)
│
├── repository/
│   └── PostRepository.java       (2. JPA репозиторий)
│
├── mapper/
│   └── PostMapper.java           (3. MapStruct маппер)
│
├── service/
│   ├── PostService.java          (4. Интерфейс сервиса)
│   └── impl/
│       └── PostServiceImpl.java  (4. Реализация сервиса)
│
└── controller/
    └── PostController.java       (5. REST контроллер)
```

На вкладке браузера `Application` в разделе `Storage` -> `local storage` -> `http://localhost:3000` проверяем, там ничего не должно быть
