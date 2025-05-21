# --- FASE DE COMPILACIÓN ---
FROM gradle:8.7.0-jdk17 AS builder
WORKDIR /app
COPY . .
RUN chmod +x gradlew
RUN --mount=type=cache,target=/home/gradle/.gradle ./gradlew clean build -x check -x test

# --- FASE DE EJECUCIÓN ---
FROM eclipse-temurin:17-jre-alpine
VOLUME /tmp

# Copiamos el JAR (ajustamos si el nombre cambia)
COPY --from=builder /app/build/libs/*.jar app.jar

ENV JAVA_OPTS=" -Xshareclasses:name=cacheapp,cacheDir=/cache,nonfatal -XX:+UseContainerSupport -XX:MaxRAMPercentage=70 -Djava.security.egd=file:/dev/./urandom"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app.jar"]