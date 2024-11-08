pipeline {
    agent any
    tools {
        jdk 'JAVA_HOME'
        maven 'M2_HOME'
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
                        script: 'curl -s -o /dev/null -w "%{http_code}" -u admin:admin "http://192.167.33.10:8087/repository/maven-releases/tn/esprit/spring/kaddem/0.0.1/kaddem-0.0.1.jar"',
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
                    sh 'docker build -t bellalounaiheb/IhebBELLALOUNA-5SAE7-G3:1.0.0 .'
                }
            }
        }


        stage('Push to Docker Hub') {
            steps {
                script {
                    def imageExists = sh(
                        script: 'curl -s -o /dev/null -w "%{http_code}" -u admin:admin "https://hub.docker.com/v2/repositories/bellalounaiheb/kaddem/tags/1.0.0/"',
                        returnStdout: true
                    ).trim()

                    if (imageExists != '200') {
                        withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USERNAME')]) {
                            sh 'echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin'
                            sh 'docker push bellalounaiheb/IhebBELLALOUNA-5SAE7-G3'
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
                    def backExists = sh(script: 'docker ps -a --filter "name=back" --format "{{.Names}}"', returnStdout: true).trim()
                    def dbExists = sh(script: 'docker ps -a --filter "name=mysqldb" --format "{{.Names}}"', returnStdout: true).trim()

                    if (backExists) {
                        sh 'docker stop back || true'
                        sh 'docker rm back || true'
                    }
                    if (dbExists) {
                        sh 'docker stop mysqldb || true'
                        sh 'docker rm mysqldb || true'
                    }

                    sh 'docker compose up -d'
                }
            }
        }
    }
}