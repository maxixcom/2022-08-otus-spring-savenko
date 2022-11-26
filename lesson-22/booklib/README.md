# Домашнее задание к занятию #22 - Spring WebFlux

## Задача

Использовать WebFlux

**Цель**: разрабатывать Responsive и Resilent приложения на реактивном стеке Spring c помощью Spring Web Flux и Reactive Spring Data Repositories
**Результат**: приложение на реактивном стеке Spring

**Описание/Пошаговая инструкция выполнения домашнего задания:**

1. За основу для выполнения работы можно взять ДЗ с Ajax + REST (классическое веб-приложение для этого луче не использовать).
2. Но можно выбрать другую доменную модель (не библиотеку).
3. Необходимо использовать Reactive Spring Data Repositories.
4. В качестве БД лучше использовать MongoDB или Redis. Использовать PostgreSQL и экспериментальную R2DBC не рекомендуется.
5. RxJava vs Project Reactor - на Ваш вкус.
6. Вместо классического Spring MVC и embedded Web-сервера использовать WebFlux.
7. Опционально: реализовать на Functional Endpoints

Данное задание НЕ засчитывает предыдущие!

Рекомендации:
Старайтесь избавиться от лишних архитектурных слоёв. Самый простой вариант - весь flow в контроллере.

## Требования для сборки и выполнения

- Java 11 (JDK)
- Mongo

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
