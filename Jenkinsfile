pipeline {
    agent any
    tools {
        jdk 'JAVA_HOME'
        maven 'M2_HOME'
    }
    environment {
        DOCKER_IMAGE = "yasminerajhi/yasminerajhi-5sae7-g3" // Docker image name
    }
    stages {
        stage('GIT') {
            steps {
                git branch: 'YasmineRAJHI-5SAE7-G3',
                    url: 'https://github.com/bellalounaiheb/5SAE7-G3-Kaddem.git'
            }
        }

        stage('Clean Stage') {
            steps {
                sh 'mvn clean'
            }
        }

        stage('Compile Stage') {
            steps {
                sh 'mvn compile'
            }
        }

        stage('Unit Testing') {
            steps {
                // Run the tests
                sh 'mvn test'
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package -DskipTests'
            }
        }

        stage('Install') {
            steps {
                sh 'mvn install -DskipTests'
            }
        }

        stage('SonarQube') {
            steps {
                sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=s0nAr_qube__ -Dmaven.test.skip=true'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t kaddem:latest .'
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', 'dockerhub-credentials-id') {
                        docker.image("${DOCKER_IMAGE}:${env.BUILD_NUMBER}").push()
                        // Optionally push a 'latest' tag for easy reference
                        docker.image("${DOCKER_IMAGE}:${env.BUILD_NUMBER}").push('latest')
                    }
                }
            }
        }

        stage('Nexus') {
            steps {
                sh 'mvn deploy -Dmaven.test.skip=true'
            }
        }
    }
}