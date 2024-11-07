FROM openjdk:17-jdk-alpine
LABEL authors="Iheb Bellalouna"
EXPOSE 8089
ARG JAR_FILE
COPY target/${JAR_FILE} /kaddem-0.0.1.jar
ENTRYPOINT ["java", "-jar", "/kaddem-0.0.1.jar"]