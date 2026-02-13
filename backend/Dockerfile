FROM ubuntu:latest AS deb

ENV JAVA_TOOL_OPTIONS -Dfile.encoding=UTF8 -Xmx2G

ENV TZ="Europe/Moscow"

RUN apt-get update && apt-get install -y locales

RUN sed -i -e \
  's/# ru_RU.UTF-8 UTF-8/ru_RU.UTF-8 UTF-8/' /etc/locale.gen \
   && locale-gen

ENV JAVA_TOOL_OPTIONS -Dfile.encoding=UTF8 -Xmx2G
ENV LANG ru_RU.UTF-8
ENV LANGUAGE ru_RU:ru
ENV LC_LANG ru_RU.UTF-8
ENV LC_ALL ru_RU.UTF-8

FROM maven:3.9.5-eclipse-temurin-21-alpine AS maven-dependencies

WORKDIR /app

COPY ./pom.xml /app/

RUN mvn dependency:go-offline verify --fail-never

FROM maven-dependencies AS maven-builder

COPY ./src /app/src

RUN mvn clean install -DskipTests=true

FROM eclipse-temurin:21-alpine

WORKDIR /app

COPY --from=maven-builder /app/target/chirp-service-0.0.1-SNAPSHOT.jar /app/app.jar

ENTRYPOINT java -jar /app/app.jar
