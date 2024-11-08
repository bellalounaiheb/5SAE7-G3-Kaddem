pipeline {
    agent any

    environment {
        // Docker Hub credentials
        DOCKER_USER = 'mouheb.naddari@esprit.tn'
        DOCKER_PASS = 'Hunterxhunter1***'
        DOCKER_REPO = 'mouhebn/kaddem'
    }

    stages {
        // Stage 1: Git Checkout
        stage('GIT Checkout') {
            steps {
                // Clone the repository from GitHub
                git branch: 'MouhebNaddari-5SAE7-G3',
                    url: 'https://github.com/bellalounaiheb/5SAE7-G3-Kaddem.git'
            }
        }

        // Stage 2: Compile and Build Project (Maven)
        stage('Build Project') {
            steps {
                // Clean, compile, and package the project
                sh 'mvn clean install'
            }
        }

        stage('Unit Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('SonarQube') {
           steps {
            retry(3) {
             sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=Hunterxhunter1*** -Dmaven.test.skip=true'
              }
            }
        }

         stage('Nexus Deployment') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'SUCCESS') {
                    sh 'mvn deploy -Dmaven.test.skip=true'
                }
            }
         }
        // Stage 3: Build Docker Image
        stage('Build Docker Image') {
            steps {
                script {
                    // Ensure the JAR file is present in the target directory before building the Docker image
                    sh 'ls -l target/'

                    // Build Docker image and tag it with your Docker Hub repository
                    sh 'docker build -t kaddem:latest .'

                    // Tag the image with the correct Docker Hub repository name
                    sh 'docker tag kaddem:latest mouhebn/kaddem:latest'
                }
            }
        }

        // Stage 4: Push Docker Image to Docker Hub
        stage('Push Docker Image') {
            steps {
                script {
                    // Login to Docker Hub using credentials
                    sh """
                        echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin
                        docker push $DOCKER_REPO:latest
                    """
                }
            }
        }

        // Stage 5: Run Docker Compose
        stage('Docker Compose Up') {
            steps {
                script {
                sh 'docker compose -f docker-compose.yml up -d'
                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}
