FROM openjdk:17-jdk-alpine

WORKDIR /app

RUN wget "http://192.167.33.10:8081/repository/maven-releases/tn/esprit/spring/kaddem/0.0.1/kaddem-0.0.1.jar" -O app.jar


EXPOSE 8089

ENTRYPOINT ["java", "-jar", "app.jar"]