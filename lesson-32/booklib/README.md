# Домашнее задание к занятию #32 - Монолиты vs. Microservices (Round 2), Spring Boot Actuator - must have в микросервисах

## Задача

Использовать метрики, healthchecks и logfile

**Цель**: реализовать production-grade мониторинг и прозрачность в приложении
**Результат**: приложение с применением Spring Boot Actuator

**Описание/Пошаговая инструкция выполнения домашнего задания:**

Данное задание выполняется на основе одного из реализованных Web-приложений

1. Подключить Spring Boot Actuator в приложение.
2. Включить метрики, healthchecks и logfile.
3. Реализовать свой собственный HealthCheck индикатор
4. UI для данных от Spring Boot Actuator реализовывать не нужно.
5. Опционально: переписать приложение на HATEOAS принципах с помощью Spring Data REST Repository

Данное задание НЕ засчитывает предыдущие!

## Реализация

За основу взято приложение "Библиотека" MVC.

Актуатор доступен по ссылке [http://localhost:8080/actuator](http://localhost:8080/actuator)

BookCountHealthIndicator реализует проверку, что библиотека не пуста.

Spring data rest с доступен по ссылке [http://localhost:8080/datarest](http://localhost:8080/datarest)

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
