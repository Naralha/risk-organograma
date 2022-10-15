FROM openjdk:11-slim

LABEL maintainer="Illary Huaylupo <illaryhs@gmail.com>"

ARG JAR_FILE

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]