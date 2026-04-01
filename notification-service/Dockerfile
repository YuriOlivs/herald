# STAGE 1: BUILD

FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /build

COPY notification-shared/pom.xml notification-shared/pom.xml
COPY notification-shared/src notification-shared/src
RUN mvn -f notification-shared/pom.xml install -DskipTests

COPY notification-service/pom.xml notification-service/pom.xml
COPY notification-serivce/src notification-service/src
RUN mvn -f notification-service/pom.xml package -DskipTests

# STAGE 2: RUNTIME
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=build /build/notification-service/target/*.jar app.jar

# This service does not need to expose HTTP port
# But it can be used for a health check endpoint
#EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]