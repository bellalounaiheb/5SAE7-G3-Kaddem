pipeline {
    agent any
    tools {
        jdk 'JAVA_HOME'
        maven 'M2_HOME'
    }
    stages {
        stage('GIT') {
            steps {
                git branch: 'ZiedGHANEM-5SAE7-G3',
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
                                 // Run the tests
                                 sh 'mvn test'
                             }
                         }



         stage('SonarQube') {
             steps {
                sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=Mohamedali_22 -Dmaven.test.skip=true'
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
                        sh "docker build -t zied22/zied_g3_kaddem ."
                        echo 'Docker image built successfully!'
                    }
                }

                stage('Pushing to DockerHub') {
                    steps {
                        withCredentials([usernamePassword(credentialsId: 'dockerhub', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                            sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'
                            sh 'docker push zied22/zied_g3_kaddem'
                        }
                    }
                }

                   stage('Running containers') {
                            steps {
                                echo 'Starting Docker containers...'
                                sh 'docker-compose up -d'
                                echo 'Containers started!'
                            }
                        }
    }
}