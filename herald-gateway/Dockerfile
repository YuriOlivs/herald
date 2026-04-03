# STAGE 1: BUILD

FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /build

COPY herald-shared/pom.xml herald-shared/pom.xml
COPY herald-shared/src herald-shared/src
RUN mvn -f herald-shared/pom.xml install -DskipTests

COPY herald-gateway/pom.xml herald-gateway/pom.xml
COPY herald-gateway/src herald-gateway/src
RUN mvn -f herald-gateway/pom.xml package -DskipTests

# STAGE 2: RUNTIME
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=build /build/herald-gateway/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]