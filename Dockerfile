# =========================
# 1) BUILD STAGE
# =========================
FROM maven:3.9.8-eclipse-temurin-17 AS build
WORKDIR /app

# Copie d'abord les fichiers Maven pour maximiser le cache
COPY pom.xml .
COPY .mvn/ .mvn/
COPY mvnw .

# Pré-télécharge les dépendances (cache-friendly)
RUN mvn -q -e -DskipTests dependency:go-offline

# Copie ensuite le code source
COPY src/ src/

# Build du jar
RUN mvn -q -DskipTests package


# =========================
# 2) RUNTIME STAGE
# =========================
FROM eclipse-temurin:17-jre
WORKDIR /app

# Sécurité: user non-root
RUN useradd -m appuser
USER appuser

# Copie uniquement l'artefact final
COPY --from=build /app/target/*.jar app.jar

# Port par défaut (PaaS peut overrider via PORT)
EXPOSE 8888

# JVM flags: container-aware + mémoire raisonnable
ENV JAVA_OPTS="-XX:MaxRAMPercentage=75 -XX:+UseContainerSupport"

# Support du port dynamique (Azure App Service aime PORT)
ENV PORT=8080

# Démarrage: on passe PORT à Spring Boot via server.port
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dserver.port=$PORT -jar app.jar"]
