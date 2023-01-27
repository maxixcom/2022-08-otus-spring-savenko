# Домашнее задание к занятию #37 - Zuul, Hystrix Circuit Breaker, Sleuth, Zipkin, Hystrix Dashboard, Secure Configuration Properties

## Задача

Обернуть внешние вызовы в Hystrix

**Цель**: сделать внешние вызовы приложения устойчивыми к ошибкам
**Результат**: приложение с изолированными с помощью Hystrix внешними вызовами

**Описание/Пошаговая инструкция выполнения домашнего задания:**

1. Обернуть все внешние вызовы в Hystrix, Hystrix Javanica.
2. Возможно использование Resilent4j
3. Возможно использование Feign Client

Опционально: Поднять Turbine Dashboard для мониторинга.

Данное задание НЕ засчитывает предыдущие!

## Реализация

За основу взято приложение "Библиотека" REST.

В качестве Circuit breaker используется Resilent4j. 

Для мониторинга "предохранителей" можно использовать актуатор: 

[http://localhost:8080/actuator/health](http://localhost:8080/actuator/health)


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
