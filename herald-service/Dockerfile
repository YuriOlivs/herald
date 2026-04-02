# STAGE 1: BUILD

FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /build

COPY herald-shared/pom.xml herald-shared/pom.xml
COPY herald-shared/src herald-shared/src
RUN mvn -f herald-shared/pom.xml install -DskipTests

COPY herald-service/pom.xml herald-service/pom.xml
COPY herald-serivce/src herald-service/src
RUN mvn -f herald-service/pom.xml package -DskipTests

# STAGE 2: RUNTIME
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=build /build/herald-service/target/*.jar app.jar

# This service does not need to expose HTTP port
# But it can be used for a health check endpoint
#EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]