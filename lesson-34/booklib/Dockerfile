#FROM openjdk:11.0.9.1-jdk as builder
FROM gradle:7.5-jdk17-alpine as builder
MAINTAINER Maxim Savenko <maxim.savenko@gmail.com>

COPY . /app

WORKDIR /app

RUN gradle build

FROM openjdk:17-jdk

EXPOSE 8080/tcp

ENV JAVA_OPTS="-XX:+UseContainerSupport"

COPY --from=builder /app/build/libs/booklib-0.0.1-SNAPSHOT.jar /app/app.jar

WORKDIR app

ENTRYPOINT ["/bin/sh", "-c", "java ${JAVA_OPTS} -jar app.jar"]