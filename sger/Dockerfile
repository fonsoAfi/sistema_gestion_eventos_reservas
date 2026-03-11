FROM eclipse-temurin:21-jdk-alpine

# FROM eclipse-temurin:21-jre-alpine # PROD

WORKDIR /sger

ARG JAR_FILE=target/sger-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} /app_sger.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app_sger.jar"]