# ... (Tu FASE DE CONSTRUCCIÓN COMPLETA aquí, que debe pasar) ...
FROM gradle:8.7.0-jdk17 AS builder
WORKDIR /app
COPY . /app/
RUN chmod +x gradlew
RUN --mount=type=cache,target=/home/gradle/.gradle ./gradlew clean build -x check -x test


# --- FASE DE EJECUCIÓN (RUNTIME STAGE) ---
FROM eclipse-temurin:17-jre-alpine

VOLUME /tmp

# AJUSTA ESTA LÍNEA A LA RUTA CORRECTA DE TU JAR EN LA FASE BUILDER
# Si tu rootProject.name es 'NameProject' y tu módulo principal es 'app-service'
# y tienes projectDir personalizado, podría ser:
ARG JAR_PATH=./applications/app-service/build/libs/NameProject.jar # <-- Posible ruta correcta

COPY --from=builder ${JAR_PATH} app.jar

# TEMPORALMENTE, CAMBIA EL ENTRYPOINT PARA INSPECCIÓN
# En lugar de ejecutar la app, entra al shell del contenedor
# ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /app.jar" ]
ENTRYPOINT [ "sh" ] # <-- CAMBIO PARA DEPURACIÓN

ENV JAVA_OPTS=" -Xshareclasses:name=cacheapp,cacheDir=/cache,nonfatal -XX:+UseContainerSupport -XX:MaxRAMPercentage=70 -Djava.security.egd=file:/dev/./urandom"

RUN addgroup --system appgroup && adduser --system --ingroup appgroup appuser
USER appuser