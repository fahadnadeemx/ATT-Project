FROM openjdk:11
EXPOSE 8080
ADD target/book-management.jar att-project.jar
ENTRYPOINT ["java", "-jar", "/att-project.jar"]
