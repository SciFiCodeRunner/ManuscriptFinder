FROM gradle:8.7.0-jdk17 AS builder

WORKDIR /app

COPY settings.gradle .
COPY build.gradle .
COPY gradlew .
COPY gradle gradle/
COPY src src/

RUN chmod +x gradlew

RUN --mount=type=cache,target=/home/gradle/.gradle ./gradlew clean build -x check -x test

FROM eclipse-temurin:17-jre-alpine

VOLUME /tmp

ARG JAR_FILE=build/libs/*.jar
COPY --from=builder ${JAR_FILE} app.jar

ENV JAVA_OPTS=" -Xshareclasses:name=cacheapp,cacheDir=/cache,nonfatal -XX:+UseContainerSupport -XX:MaxRAMPercentage=70 -Djava.security.egd=file:/dev/./urandom"

RUN addgroup --system appgroup && adduser --system --ingroup appgroup appuser
USER appuser

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /app.jar" ]