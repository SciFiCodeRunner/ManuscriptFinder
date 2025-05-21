# --- FASE DE COMPILACIÓN ---
FROM gradle:8.7.0-jdk17 AS builder
WORKDIR /app

# Agrega el código fuente
COPY . .

# Verificamos contenido inicial
RUN echo "== Contenido inicial en /app ==" && ls -la /app

# Damos permisos y construimos
RUN chmod +x gradlew
RUN echo "== Ejecutando Gradle build ==" && \
    ./gradlew clean build -x check -x test && \
    echo "== Contenido en app-service/build/libs después del build ==" && \
    ls -la /app/app-service/build/libs

# --- FASE DE EJECUCIÓN ---
FROM eclipse-temurin:17-jre-alpine
VOLUME /tmp

# Mostramos contenido antes de copiar
RUN echo "== Preparando contenedor de ejecución =="

# Copiamos el JAR generado
COPY --from=builder /app/app-service/build/libs/*.jar app.jar

# Verificamos que el JAR esté presente
RUN echo "== Contenido final del contenedor ==" && ls -la /

# Configuración de variables
ENV JAVA_OPTS=" -Xshareclasses:name=cacheapp,cacheDir=/cache,nonfatal -XX:+UseContainerSupport -XX:MaxRAMPercentage=70 -Djava.security.egd=file:/dev/./urandom"

# Comando de ejecución
ENTRYPOINT ["sh", "-c", "echo '== Ejecutando app.jar ==' && java $JAVA_OPTS -jar /app.jar"]
