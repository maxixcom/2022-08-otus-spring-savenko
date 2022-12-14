# Домашнее задание к занятию #18 - Spring MVC View

## Задача

CRUD приложение с Web UI и хранением данных в БД

**Цель**: разрабатывать полноценные классические Web-приложения
**Результат**: Web-приложение полностью на стеке Spring

**Описание/Пошаговая инструкция выполнения домашнего задания:**

1. Создать приложение с хранением сущностей в БД (можно взять библиотеку и DAO/репозитории из прошлых занятий)
2. Использовать классический View на Thymeleaf, classic Controllers.
3. Для книг (главной сущности) на UI должны быть доступные все CRUD операции. CRUD остальных сущностей - по желанию/необходимости.
4. Локализацию делать НЕ нужно - она строго опциональна.

Данное задание НЕ засчитывает предыдущие!

Это домашнее задание частично будет использоваться в дальнейшем

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
