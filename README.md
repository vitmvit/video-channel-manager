# video-channel-manager

## Задача

Требуется написать REST веб-сервис для работы с каналами видеохостинга.

#### Сущности:

1. Канал
   Канал имеет название, краткое описание, автора (среди зарегистрированных пользователей), неограниченное число
   подписчиков (тоже среди зарегистрированных пользователей),
   дату создания, один основной язык, аватарку, одну категорию

2. Пользователь
   Каждый пользователь имеет никнейм, имя, почту и может подписаться на неограниченное число каналов

#### Необходимые операции:

- создание и редактирование информации о пользователе
- создание и редактирование информации о канале
- подписка пользователя на канал
- отписка пользователя с канала
- отображение списка всех каналов с пагинацией и фильтрацией по названию, языку и категории.
  список должен содержать название, количество подписчиков, язык, аватарку, категорию
- отображение списка всех подписок пользователя (без пагинации)
  список должен содержать только название каналов
- получение подробной информации по каналу (все, что в списке + описание, автор, дата создания)

#### Требования:

- задание должно включать полное описание (документацию) спроектированного веб-сервиса: эндпоинты, параметры, форматы
  запросов и ответов, http статусы и т.д.
- соблюдать принципы REST
- должны быть предусмотрены исключительные ситуации
- запросы должны использовать подходящий http статус

## Swagger

http://localhost:8080/api/doc/swagger-ui/index.html#

#### Технологии:

- Java 17
- Spring Boot
- Gradle
- Jpa Repository
- MapStruct
- Lombok
- Postgres
- Spring Data Repositories 
- Java Streams

## Контроллеры

### UserController

#### GET ResponseEntity<UserDto> findById(@PathVariable("id") Long id):

Request:

```http request
http://localhost:8080/api/users/4
```

Response:

```json
{
  "id": 4,
  "nickname": "user7",
  "name": "user7",
  "email": "user7@mail.com"
}
```

Entity not found if user not found:

```json
{
  "errorMessage": "Entity not found!",
  "errorCode": 404
}
```

#### GET ResponseEntity<List<ChannelNameDto>> getChannelsSubscribe(@PathVariable("id") Long id):

Request:

```http request
http://localhost:8080/api/users/channels/2
```

Response:

```json
[
  {
    "name": "channel1"
  }
]
```

Empty list:

```json
[]
```

#### POST ResponseEntity<UserDto> create(@RequestBody UserCreateDto dto):

Request:

```http request
http://localhost:8080/api/users
```

Body:

```json
{
  "nickname": "user8",
  "name": "user8",
  "email": "user8@gmail.com"
}
```

Response:

```json
{
  "id": 5,
  "nickname": "user8",
  "name": "user8",
  "email": "user8@gmail.com"
}
```

Exception:

```json
{
  "errorMessage": "User create error",
  "errorCode": 422
}
```

#### POST ResponseEntity<Void> subscribe(@PathVariable("nameChannel") String nameChannel, @PathVariable("nickname") String nickname)

Request:

```http request
http://localhost:8080/api/users/subscribe/channel1/user1@mail.com
```

Response:

```http request
204
```

Exception:

```json
{
  "errorMessage": "Subscribe error",
  "errorCode": 422
}
```

#### POST ResponseEntity<Void> unsubscribe(@PathVariable("nameChannel") String nameChannel, @PathVariable("nickname") String nickname)

Request:

```http request
http://localhost:8080/api/users/unsubscribe/channel1/user1@mail.com
```

Response:

```http request
204
```

Exception:

```json
{
  "errorMessage": "Unsubscribe error",
  "errorCode": 422
}
```

#### PUT ResponseEntity<UserDto> update(@RequestBody UserUpdateDto dto):

Request:

```http request
http://localhost:8080/api/users
```

Body:

```json
{
  "id": 5,
  "nickname": "user8",
  "name": "user8",
  "email": "user8@gmail.com"
}
```

Response:

```json
{
  "id": 5,
  "nickname": "user888",
  "name": "user888",
  "email": "user888@gmail.com"
}
```

Exception:

```json
{
  "errorMessage": "User update error",
  "errorCode": 422
}
```

#### DELETE ResponseEntity<Void> delete(@PathVariable("id") Long id):

Request:

```http request
http://localhost:8080/api/users/1
```

Response:

```http request
204
```

### ChannelController

#### GET ResponseEntity<ChannelDto> findById(@PathVariable("id") Long id) :

Request:

```http request
http://localhost:8080/api/channels/14
```

Response:

```json
{
  "id": 14,
  "name": "channel_ONE",
  "description": "channel_ONE",
  "author": {
    "id": 3,
    "nickname": "user2@mail.com",
    "name": "user2@mail.com",
    "email": "user2@mail.com"
  },
  "countSubscribers": 1,
  "creationDate": "2024-09-29T14:50:37.711",
  "mainLanguage": "ENGLISH",
  "avatar": "b3505649-24f9-4592-91a0-84cef8a1d46d",
  "category": "MUSIC"
}
```

Entity not found if user not found:

```json
{
  "errorMessage": "Entity not found!",
  "errorCode": 404
}
```

#### GET ResponseEntity<Page<ChannelInfoDto>> getAll(@RequestParam(value = "offset", defaultValue = "0") Integer offset, @RequestParam(value = "limit", defaultValue = "15") Integer limit):

Request:

```http request
http://localhost:8080/api/channels
```

Response:

```json
{
  "content": [
    {
      "id": 14,
      "name": "channel_ONE",
      "countSubscribers": 1,
      "mainLanguage": "ENGLISH",
      "avatar": "b3505649-24f9-4592-91a0-84cef8a1d46d",
      "category": "MUSIC"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 15,
    "sort": {
      "empty": true,
      "unsorted": true,
      "sorted": false
    },
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "totalPages": 1,
  "totalElements": 1,
  "last": true,
  "size": 15,
  "number": 0,
  "sort": {
    "empty": true,
    "unsorted": true,
    "sorted": false
  },
  "numberOfElements": 1,
  "first": true,
  "empty": false
}
```

Request:

```http request
http://localhost:8080/api/channels?offset=0&limit=1
```

Response:

```json
{
  "content": [
    {
      "id": 14,
      "name": "channel_ONE",
      "countSubscribers": 1,
      "mainLanguage": "ENGLISH",
      "avatar": "b3505649-24f9-4592-91a0-84cef8a1d46d",
      "category": "MUSIC"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 1,
    "sort": {
      "empty": true,
      "unsorted": true,
      "sorted": false
    },
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "totalPages": 1,
  "totalElements": 1,
  "last": true,
  "size": 1,
  "number": 0,
  "sort": {
    "empty": true,
    "unsorted": true,
    "sorted": false
  },
  "numberOfElements": 1,
  "first": true,
  "empty": false
}
```

#### POST ResponseEntity<ChannelDto> create(@RequestBody ChannelCreateDto dto):

Request:

```http request
http://localhost:8080/api/channels
```

Body:

```json
{
  "name": "channel_one",
  "description": "channel_one",
  "author": {
    "id": 2,
    "nickname": "user1@mail.com",
    "name": "user1@mail.com",
    "email": "user1@mail.com"
  },
  "mainLanguage": "ENGLISH",
  "avatar": "b3505649-24f9-4592-91a0-84cef8a1d46d",
  "category": "MUSIC"
}
```

Response:

```json
{
  "id": 7,
  "name": "channel_one",
  "description": "channel_one",
  "author": {
    "id": 2,
    "nickname": "user1@mail.com",
    "name": "user1@mail.com",
    "email": "user1@mail.com"
  },
  "countSubscribers": 0,
  "creationDate": "2024-09-29T13:55:54.647",
  "mainLanguage": "ENGLISH",
  "avatar": "b3505649-24f9-4592-91a0-84cef8a1d46d",
  "category": "MUSIC"
}
```

Exception:

```json
{
  "errorMessage": "Channel create error",
  "errorCode": 422
}
```

#### PUT ResponseEntity<ChannelDto> update(@RequestBody ChannelUpdateDto dto):

Request:

```http request
http://localhost:8080/api/channels
```

Body:

```json
{
  "id": 7,
  "name": "channel_ONE",
  "description": "channel_one",
  "author": {
    "id": 2,
    "nickname": "user1@mail.com",
    "name": "user1@mail.com",
    "email": "user1@mail.com"
  },
  "mainLanguage": "ENGLISH",
  "avatar": "b3505649-24f9-4592-91a0-84cef8a1d46d",
  "category": "MUSIC"
}
```

Response:

```json
{
  "id": 7,
  "name": "channel_ONE",
  "description": "channel_one",
  "author": {
    "id": 2,
    "nickname": "user1@mail.com",
    "name": "user1@mail.com",
    "email": "user1@mail.com"
  },
  "countSubscribers": 0,
  "creationDate": "2024-09-29T13:55:54.647",
  "mainLanguage": "ENGLISH",
  "avatar": "b3505649-24f9-4592-91a0-84cef8a1d46d",
  "category": "MUSIC"
}
```

Exception:

```json
{
  "errorMessage": "Channel update error",
  "errorCode": 422
}
```

#### DELETE ResponseEntity<Void> delete(@PathVariable("id") Long id):

Request:

```http request
http://localhost:8080/api/channels/1
```

Response:

```http request
204
```