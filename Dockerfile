FROM openjdk:8-jdk-alpine
ADD target/*.jar app.jar
ENTRYPOINT ["sh", "-c", "java -jar -Dspring.profiles.active=dev /app.jar"]