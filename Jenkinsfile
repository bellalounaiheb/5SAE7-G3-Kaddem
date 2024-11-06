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

        stage('MVN CLEAN') {
            steps {
                sh 'mvn clean'
            }
        }

        stage('MVN COMPILE') {
            steps {
                sh 'mvn compile'
            }
        }

        stage('Tests - Mockito/JUnit') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Rapport JaCoCo') {
            steps {
                sh 'mvn test'
                sh 'mvn jacoco:report'
            }
        }

        stage('JaCoCo coverage report') {
            steps {
                step([$class: 'JacocoPublisher',
                      execPattern: '**/target/jacoco.exec',
                      classPattern: '**/classes',
                      sourcePattern: '**/src',
                      exclusionPattern: '*/target/**/,**/*Test*,**/*_javassist/**'
                ])
            }
        }

        stage('SonarQube') {
            steps {
                sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=Sonarqube12345# -Dmaven.test.skip=true'
            }
        }

        stage('Deploy to Nexus') {
            steps {
                sh 'mvn deploy'
            }
        }
    }

       stage('Email Notification') {
            steps {
                script {
                    currentBuild.result = 'SUCCESS'
                    emailext(
                        subject: "Build #${currentBuild.number} Successful: ${currentBuild.fullDisplayName}",
                        body: """
                            The build was successful!
                            Build Details: ${BUILD_URL}
                            Build Number: ${currentBuild.number}
                            Build Status: ${currentBuild.currentResult}
                        """,
                        to: 'malek.khelil@esprit.tn'
                    )
                }
            }
        }
    }
    post {
       success {
           emailext (
           subject: "Build Successful",
           body: "The build was successful. Job: ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}",
           to: "malek.khelil@esprit.tn"
                                )
                            }
       failure {
           emailext (
           subject: "Build Failed",
            body: "The build failed. Job: ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}\n\nConsole Output:\n${currentBuild.rawBuild.getLog(100)}",
            to: "malek.khelil@esprit.tn",
             attachLog: true
            )
       }
    }
}