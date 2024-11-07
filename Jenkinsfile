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

        stage('Docker Build and Compose Up') {
                    steps {
                        script {
                            sh 'docker build -t myapp-image --build-arg JAR_FILE=kaddem-0.0.1.jar .'
                            sh 'docker-compose up -d'
                        }
                    }
                }

    }
}