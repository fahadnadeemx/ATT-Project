FROM openjdk:8
EXPOSE 8080
ADD target/book-management.jar book-management.jar
ENTRYPOINT ["java", "-jar", "/book-management.jar"]