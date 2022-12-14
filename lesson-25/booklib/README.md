# Домашнее задание к занятию #25 - Spring Security: Механизмы аутентификации

## Задача

В CRUD Web-приложение добавить механизм аутентификации

**Цель**: защитить Web-приложение аутентификацией и простой авторизацией
**Результат**: приложение с использованием Spring Security

**Описание/Пошаговая инструкция выполнения домашнего задания:**

Внимание! Задание выполняется на основе нереактивного приложения Spring MVC!

1. Добавить в приложение новую сущность - пользователь. Не обязательно реализовывать методы по созданию пользователей - допустимо добавить пользователей только через БД-скрипты.
2. В существующее CRUD-приложение добавить механизм Form-based аутентификации.
3. UsersServices реализовать самостоятельно.
4. Авторизация на всех страницах - для всех аутентифицированных. Форма логина - доступна для всех.
5. Написать тесты контроллеров, которые проверяют, что все необходимые ресурсы действительно защищены.

Данное задание НЕ засчитывает предыдущие!
Данное ДЗ будет использоваться в дальнейшем.

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

## Пользователь для входа

Миграция создает первоначального пользователя с данными:

```
username: user
password: pass
```
