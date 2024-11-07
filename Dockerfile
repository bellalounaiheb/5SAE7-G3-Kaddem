FROM openjdk:17-jdk-alpine

ENV NEXUS_REPO_URL=http://192.168.33.10:8081/repository/maven-releases
ENV ARTIFACT_GROUP=tn/esprit/spring
ENV ARTIFACT_NAME=kaddem
ENV ARTIFACT_VERSION=0.0.1
ENV JAR_NAME=kaddem-O.O.1.jar

WORKDIR /app

RUN wget "${NEXUS_REPO_URL}/${ARTIFACT_GROUP}/${ARTIFACT_NAME}/${ARTIFACT_VERSION}/${JAR_NAME}" -O app.jar

EXPOSE 8089

ENTRYPOINT ["java", "-jar", "app.jar"]