pipeline {
    agent any
    tools {
        jdk 'JAVA_HOME'
        maven 'M2_HOME'
    }
    stages {
        stage('GIT') {
            steps {
                git branch: 'OnsHAMZAOUI-5SAE7-G3',
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
    }
}