pipeline {
    agent any
    tools {
        jdk 'JAVA_HOME'
        maven 'M2_HOME'
    }
    stages {
            stage('Checkout') {
                steps {
                    retry(3) { // Retry up to 3 times in case of failure
                        checkout([$class: 'GitSCM',
                                  branches: [[name: 'refs/heads/OnsHAMZAOUI-5SAE7-G3']],
                                  userRemoteConfigs: [[url: 'https://github.com/bellalounaiheb/5SAE7-G3-Kaddem.git']],
                                  extensions: [
                                      [$class: 'CheckoutOption', timeout: 60], // Increased timeout to 60 minutes
                                      [$class: 'CloneOption', depth: 1, shallow: true] // Shallow clone with depth of 1
                                  ]
                        ])
                    }
                }
            }

    /* stages {
        stage('GIT') {
            steps {
                git branch: 'OnsHAMZAOUI-5SAE7-G3',
                    url: 'https://github.com/bellalounaiheb/5SAE7-G3-Kaddem.git'
            }
        } */
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

              stage('Sonarqube') {
                    steps {
                      script {
                           def projectKey = '5SAE7-G3-Kaddem'

                          withSonarQubeEnv('sq1') {
                               sh "mvn sonar:sonar -Dsonar.projectKey=${projectKey} -DskipTests" // Skipping tests
                           }
                        }
                    }
                }

        stage('Nexus Deployment') {
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

                        stage("Stoping containers"){
                                    steps{
                                        sh "docker-compose down"
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
