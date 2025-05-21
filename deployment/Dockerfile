# --- FASE DE CONSTRUCCIÓN (BUILD STAGE) ---
FROM gradle:8.7.0-jdk17 AS builder

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia TODO el contenido del proyecto (incluyendo todos los submódulos)
# Asegúrate de que tu .dockerignore no esté excluyendo directorios necesarios
COPY . /app/

# Haz que el wrapper de Gradle sea ejecutable
RUN chmod +x gradlew

# Ejecuta la compilación de Gradle
# El --mount=type=cache ayuda a que las siguientes construcciones sean más rápidas
# -x check -x test excluye las tareas de verificación y prueba, como ya lo estabas haciendo
RUN --mount=type=cache,target=/home/gradle/.gradle ./gradlew clean build -x check -x test

# --- FASE DE EJECUCIÓN (RUNTIME STAGE) ---
FROM eclipse-temurin:17-jre-alpine

VOLUME /tmp

# Copia el JAR compilado de la fase de construcción
# Spring Boot en proyectos multi-módulo suele generar los JARs dentro de los directorios de los submódulos
# Necesitas saber cuál es el nombre del submódulo principal que produce el JAR ejecutable
# Por ejemplo, si tu módulo principal es 'main-service' y su JAR está en 'main-service/build/libs/*.jar'
# Ajusta esta línea para apuntar al JAR correcto
ARG JAR_PATH=./nombre-de-tu-submodulo-principal/build/libs/*.jar
COPY --from=builder ${JAR_PATH} app.jar

ENV JAVA_OPTS=" -Xshareclasses:name=cacheapp,cacheDir=/cache,nonfatal -XX:+UseContainerSupport -XX:MaxRAMPercentage=70 -Djava.security.egd=file:/dev/./urandom"

RUN addgroup --system appgroup && adduser --system --ingroup appgroup appuser
USER appuser

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /app.jar" ]