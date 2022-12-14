# Домашнее задание к занятию #15 - Spring Data для подключения к нереляционным БД

## Задача

Библиотеку на Spring Data JPA

**Цель**: После выполнения ДЗ вы сможете использовать Spring Data MongoDB и саму MongoDB для разработки приложений с хранением данных в нереляционной БД.
**Результат**: Приложение с использованием MongoDB

**Описание/Пошаговая инструкция выполнения домашнего задания:**

Задание может выполняться на основе предыдущего, а может быть выполнено самостоятельно
Требования:

1. Использовать Spring Data MongoDB репозитории, а если не хватает функциональности, то и *Operations
2. Тесты можно реализовать с помощью Flapdoodle Embedded MongoDB
3. Hibernate, равно, как и JPA, и spring-boot-starter-data-jpa не должно остаться в зависимостях, если ДЗ выполняется на основе предыдущего.
4. Как хранить книги, авторов, жанры и комментарии решать Вам. Но перенесённая с реляционной базы структура не всегда будет подходить для MongoDB.

Данное задание НЕ засчитывает предыдущие!

Это задание может использоваться в дальнейшем, а может не использоваться - на Ваше дальнейшее усмотрение

## Требования для сборки и выполнения

- Java 11 (JDK)

## Сборка

```shell
./gradlew build
```

## Запуск

```shell
java -jar ./build/libs/booklib-0.0.1-SNAPSHOT.jar
```

## Сборка дистрибутива

```shell
./gradlew installBootDist
```

Дистрибутив вы сможете найти в папке `build/install/booklib-boot`.

Скрипт запуска находится в папке bin дистрибутива: `build/install/booklib-boot/bin/`

Получить заархивированную версию дистрибутива можно одной из следующих команд:

```shell
./gradlew distTar
```

или

```shell
./gradlew distZip
```

## Доступные операции

После запуска, приложение перейдет в интерактивный режим и появится приглашение `shell:>`. 
Получить список доступных команд можно следующим образом:

```
shell:> help
```

**Команды управления библиотекой книг:**

| Команда                                   | Описание            |
|-------------------------------------------|---------------------|
| `n`, `create-book`                        | Создать книгу       |
| `d`, `del`, `delete-books`                | Удалить книгу       |
| `u`, `e`, `update`, `edit`, `update-book` | Редактировать книгу |
| `l`, `p`, `list`, `list-books`            | Вывести список книг |


**Управление комментариями к книгам:**

| Команда                         | Описание                     |
|---------------------------------|------------------------------|
| `nc`, `comment-book`            | Добавить комментарий к книге |
| `lc`, `pc`, `list-book-comment` | Список комментариев к книги  |
| `uc`, `ec`, `update-comment`    | Редактировать комментарий    |
| `dc`, `delete-comment`          | Удалить комментарий          |
