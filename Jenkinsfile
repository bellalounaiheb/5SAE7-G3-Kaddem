pipeline {
    agent any
    tools {
        jdk 'JAVA_HOME'
        maven 'M2_HOME'
    }
    environment {
        NEXUS_USER = credentials('nexus-credentials')
        DOCKER_USER = credentials('docker-hub-credentials')
        NEXUS_URL = 'http://192.167.33.10:8087/repository/maven-releases'
        DOCKER_IMAGE = 'bellalounaiheb/IhebBELLALOUNA-5SAE7-G3:1.0.0'
    }
    stages {

        stage('GIT') {
            steps {
                git branch: 'IhebBELLALOUNA-5SAE7-G3',
                    url: 'https://github.com/bellalounaiheb/5SAE7-G3-Kaddem.git'
            }
        }

        stage('Compile Stage') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Unit Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('SonarQube') {
            steps {
                sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=Admin123456- -Dmaven.test.skip=true'
            }
        }

        stage('Nexus Deployment') {
            steps {
                script {
                    def artifactExists = sh(
                        script: "curl -s -o /dev/null -w '%{http_code}' -u ${NEXUS_USER_USR}:${NEXUS_USER_PSW} ${NEXUS_URL}/tn/esprit/spring/kaddem/0.0.1/kaddem-0.0.1.jar",
                        returnStdout: true
                    ).trim()

                    if (artifactExists != '200') {
                        sh 'mvn deploy -Dmaven.test.skip=true'
                    } else {
                        echo 'Artifact already exists on Nexus; skipping deployment.'
                    }
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    sh "docker build -t ${DOCKER_IMAGE} ."
                }
            }
        }

        stage('Push to Docker Hub') {
            steps {
                script {
                    def imageExists = sh(
                        script: "curl -s -o /dev/null -w '%{http_code}' -u ${DOCKER_USER_USR}:${DOCKER_USER_PSW} https://hub.docker.com/v2/repositories/${DOCKER_IMAGE}/tags/1.0.0/",
                        returnStdout: true
                    ).trim()

                    if (imageExists != '200') {
                        withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USERNAME')]) {
                            sh "echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin"
                            sh "docker push ${DOCKER_IMAGE}"
                        }
                    } else {
                        echo 'Docker image already exists on Docker Hub; skipping push.'
                    }
                }
            }
        }

        stage('Deploy with Docker Compose') {
            steps {
                script {
                    sh 'docker compose down || true'
                    sh 'docker compose up -d'
                }
            }
        }

        stage('Docker Cleanup') {
            steps {
                script {
                    sh 'docker system prune -f --volumes'
                }
            }
        }
    }
}
