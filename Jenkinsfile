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

    post {
        always {
            script {
                currentBuild.result = currentBuild.currentResult
            }
            emailext subject: "Pipeline Status  ${currentBuild.result}: ${currentBuild.projectName}",
                body: """<html>
                            <body>
                                <p>Dear Team,</p>
                                <p>The pipeline for project <strong>${currentBuild.projectName}</strong> has completed with the status: <strong>${currentBuild.result}</strong>.</p>
                                <p>Thank you,</p>
                                <p>Your Jenkins Server</p>
                            </body>
                        </html>""",
                to: 'malek.kh211@gmail.com',
                mimeType: 'text/html'
        }
    }
}
