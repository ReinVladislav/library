FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/library-0.0.1-SNAPSHOT.jar /app/myapp.jar

ENTRYPOINT ["java", "-jar", "/app/myapp.jar"]

EXPOSE 8080