FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package

FROM eclipse-temurin:17-jdk
WORKDIR /app

COPY --from=build /app/target/javaapp-1.0-SNAPSHOT.jar app.jar

ENV DB_HOST=jdbc:mysql://localhost:3306/vollmed_api
    DB_USERNAME=root \
    DB_PASSWORD=salbutamol87
    JWT_SECRET=12345678

CMD ["java", "-jar", "app.jar"]