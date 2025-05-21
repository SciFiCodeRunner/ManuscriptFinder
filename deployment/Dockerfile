# --- FASE DE CONSTRUCCIÓN (BUILD STAGE) ---
# Usa una imagen con JDK y Gradle. Asegúrate de que la versión del JDK (17) coincida con la de tu proyecto.
FROM gradle:8.7.0-jdk17 AS builder

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia TODO el contenido del proyecto (incluyendo todos los submódulos, settings.gradle, build.gradle, gradlew, etc.)
# Asegúrate de que tu .dockerignore NO excluya nada necesario para la compilación.
COPY . /app/ 

# Haz que el wrapper de Gradle sea ejecutable
RUN chmod +x gradlew

# Ejecuta la compilación de Gradle
# El --mount=type=cache ayuda a que las siguientes construcciones sean más rápidas.
# Los `-x check -x test` excluyen tareas si no son estrictamente necesarias para el despliegue.
# Si resolviste el problema de validateStructure, esta línea debería pasar.
RUN --mount=type=cache,target=/home/gradle/.gradle ./gradlew clean build -x check -x test

# --- FASE DE EJECUCIÓN (RUNTIME STAGE) ---
# Usa una imagen más ligera solo con el JRE para ejecutar la aplicación.
# 'jre-alpine' es más pequeño que 'jdk-alpine'.
FROM eclipse-temurin:17-jre-alpine 

# Establece un volumen para el sistema de archivos temporal
VOLUME /tmp

# Copia el JAR compilado de la fase de construcción a la fase de ejecución.
# AJUSTA ESTA LÍNEA para apuntar al JAR ejecutable de tu SUBMÓDULO PRINCIPAL.
# Si tu módulo principal es 'app-service' y tu rootProject.name es 'NameProject',
# el JAR se generaría en './app-service/build/libs/NameProject.jar'.
ARG JAR_PATH=./app-service/build/libs/*.jar # <-- Asegúrate de que esta ruta sea correcta para tu JAR principal
COPY --from=builder ${JAR_PATH} app.jar

# Configura las opciones de la JVM (ajusta la memoria si es necesario)
ENV JAVA_OPTS=" -Xshareclasses:name=cacheapp,cacheDir=/cache,nonfatal -XX:+UseContainerSupport -XX:MaxRAMPercentage=70 -Djava.security.egd=file:/dev/./urandom"

# Crea un usuario no root y cambia a él por seguridad
RUN addgroup --system appgroup && adduser --system --ingroup appgroup appuser
USER appuser

# Define el comando para ejecutar la aplicación cuando el contenedor se inicia
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /app.jar" ]