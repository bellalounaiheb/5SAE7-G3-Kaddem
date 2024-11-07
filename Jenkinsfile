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
                sh 'mvn deploy -Dmaven.test.skip=true'
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    sh 'docker build -t bellalounaiheb/alpine:1.0.0 .'
                }
            }
        }
        stage('Deploy with Docker Compose') {
            steps {
                script {
                    def backExists = sh(script: 'docker ps -a --filter "name=back" --format "{{.Names}}"', returnStdout: true).trim()
                    def dbExists = sh(script: 'docker ps -a --filter "name=mysqldb" --format "{{.Names}}"', returnStdout: true).trim()

                    if (backExists) {
                        sh 'docker stop back'
                        sh 'docker rm back'
                    }
                    if (dbExists) {
                        sh 'docker stop mysqldb'
                        sh 'docker rm mysqldb'
                    }

                    sh 'docker compose up -d'
                }
            }
        }
    }
}