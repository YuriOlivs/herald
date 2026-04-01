# STAGE 1: BUILD

FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /build

COPY notification-shared/pom.xml notification-shared/pom.xml
COPY notification-shared/src notification-shared/src
RUN mvn -f notification-shared/pom.xml install -DskipTests

COPY notification-scheduler/pom.xml notification-scheduler/pom.xml
COPY notification-scheduler/src notification-scheduler/src
RUN mvn -f notification-scheduler/pom.xml package -DskipTests

# STAGE 2: RUNTIME
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=build /build/notification-scheduler/target/*.jar app.jar

#EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]