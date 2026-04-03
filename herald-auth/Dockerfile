# STAGE 1: BUILD

FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /build

COPY herald-shared/pom.xml herald-shared/pom.xml
COPY herald-shared/src herald-shared/src
RUN mvn -f herald-shared/pom.xml install -DskipTests

COPY herald-auth/pom.xml herald-auth/pom.xml
COPY herald-serivce/src herald-auth/src
RUN mvn -f herald-auth/pom.xml package -DskipTests

# STAGE 2: RUNTIME
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=build /build/herald-auth/target/*.jar app.jar

EXPOSE 8084

ENTRYPOINT ["java", "-jar", "app.jar"]