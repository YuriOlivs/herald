# STAGE 1: BUILD

FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /build

COPY notification-shared/pom.xml notification-shared/pom.xml
COPY notification-shared/src notification-shared/src
RUN mvn -f notification-shared/pom.xml install -DskipTests

COPY notification-gateway/pom.xml notification-gateway/pom.xml
COPY notification-gateway/src notification-gateway/src
RUN mvn -f notification-gateway/pom.xml package -DskipTests

# STAGE 2: RUNTIME
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=build /build/notification-gateway/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]