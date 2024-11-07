FROM openjdk:17-jdk-alpine
LABEL authors="Iheb Bellalouna"
EXPOSE 8089
RUN apk add --no-cache curl

ARG JAR_FILE_URL="http://192.167.33.10:8087/repository/maven-releases/tn/esprit/spring/kaddem/0.0.1/kaddem-0.0.1.jar"

RUN curl -u "admin:admin" -L $JAR_FILE_URL -o /kaddem-0.0.1.jar

ENTRYPOINT ["java", "-jar", "/kaddem-0.0.1.jar"]