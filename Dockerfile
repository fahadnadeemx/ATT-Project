FROM maven:3.8.3-openjdk-11 AS build
WORKDIR /ATT-Project
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ /ATT-Project/src/
RUN mvn package -DskipTests

FROM openjdk:11-jre-slim
WORKDIR /ATT-Project
COPY --from=build /target/*.jar /book-management.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/book-management.jar"]