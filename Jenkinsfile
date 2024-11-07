pipeline {
    agent any
    tools {
        jdk 'JAVA_HOME'
        maven 'M2_HOME'
    }
    stages {
        stage('GIT') {
            steps {
                git branch: 'MalekKHELIL-5SAE7-G3',
                    url: 'https://github.com/bellalounaiheb/5SAE7-G3-Kaddem.git'
            }
        }

        stage('Maven Clean and Compile') {
            steps {
                echo 'Running Maven Clean and Compile'
                sh 'mvn clean compile'
            }
        }

        stage('Maven Install') {
            steps {
                echo 'Running Maven Install'
                sh 'mvn install'
            }
        }

        stage('Tests - JUnit/Mockito') {
            steps {
                echo 'Running Tests'
                sh 'mvn test'
            }
        }

        stage('Generate JaCoCo Report') {
            steps {
                echo 'Generating JaCoCo Report'
                sh 'mvn jacoco:report'
            }
        }

        stage('JaCoCo Coverage Report') {
            steps {
                echo 'Publishing JaCoCo Coverage Report'
                step([$class: 'JacocoPublisher',
                      execPattern: '**/target/jacoco.exec',
                      classPattern: '**/classes',
                      sourcePattern: '**/src',
                      exclusionPattern: '/target/**/,**/*Test,**/*_javassist/**'
                ])
            }
        }

        stage('SonarQube') {
            steps {
                echo 'Running SonarQube Analysis'
                sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=Sonarqube12345#'
            }
        }

        stage('Deploy to Nexus') {
            steps {
                echo 'Deploying to Nexus Repository'
                sh 'mvn clean deploy'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    echo 'Building Docker Image'
                    def dockerImage = docker.build("malekkh/kaddem:0.0.1")
                }
            }
        }

        stage('Deploy Image to DockerHub') {
            steps {
                script {
                    echo 'Logging into DockerHub and Pushing Image'
                    sh 'docker login -u malekkh -p dockerpass12345?'
                    sh 'docker push malekkhelil/kaddem:0.0.1'
                }
            }
        }

        stage('Deploy with Docker Compose') {
            steps {
                script {
                    echo 'Deploying with Docker Compose'
                    sh 'docker-compose up -d'
                }
            }
        }
    }
    post {
        success {
            mail to: 'malek.kh211@gmail.com',
                 subject: "Pipeline Jenkins - Success - Build #${BUILD_NUMBER}",
                 body: """Pipeline Jenkins

                 Final Report: The pipeline has completed successfully. Build number: ${BUILD_NUMBER}. No action required."""
        }
        failure {
            mail to: 'malek.kh211@gmail.com',
                 subject: "Pipeline Jenkins - Failure - Build #${BUILD_NUMBER}",
                 body: """Pipeline Jenkins

                 Final Report: The pipeline has failed. Build number: ${BUILD_NUMBER}. Please check the logs and take necessary actions."""
        }
        always {
            echo 'Pipeline completed.'
        }
    }
}
