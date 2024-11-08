pipeline {
    agent any
    tools {
        jdk 'JAVA_HOME'
        maven 'M2_HOME'
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

         stage('Nexus') {
            steps {
                sh 'mvn deploy -Dmaven.test.skip=true'
            }
         }

        stage("Docker Build") {
            steps {
                echo "Building Docker image..."
                sh "docker build -t yasminerajhi/yasminerajhi_5sae7_g3 ."
                echo 'Docker image built successfully!'
            }
        }

        stage('Pushing to DockerHub') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials-id', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'
                    sh 'docker push yasminerajhi/yasminerajhi_5sae7_g3'
                }
            }
        }

        stage("Stoping containers"){
            steps{
                sh "docker compose down"
            }
        }

        stage('Running containers') {
            steps {
                echo 'Starting Docker containers...'
                sh 'docker compose up -d'
                echo 'Containers started!'
            }
        }
    }
}