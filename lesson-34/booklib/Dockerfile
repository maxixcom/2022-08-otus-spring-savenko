FROM gradle:7.5-jdk17-alpine as builder
MAINTAINER Maxim Savenko <maxim.savenko@gmail.com>

COPY . /app

WORKDIR /app

RUN gradle bootJar

FROM openjdk:17-jdk

EXPOSE 8080/tcp

ENV JAVA_OPTS="-XX:+UseContainerSupport"

COPY --from=builder /app/build/libs/*.jar /app/app.jar

WORKDIR app

ENTRYPOINT ["/bin/sh", "-c", "java ${JAVA_OPTS} -jar app.jar"]