FROM openjdk:21-bookworm
ARG JAR_FILE=target/*.jar
COPY ./target/taskReputeo-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]