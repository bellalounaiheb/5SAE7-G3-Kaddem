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

        stage('MVN SONARQUBE') {
            steps {
                echo 'Running SonarQube analysis'
                script {
                    def projectKey = '5SAE7-G3-Kaddem'
                    withSonarQubeEnv('sq1') {
                        sh "mvn sonar:sonar -Dsonar.projectKey=${projectKey} -DskipTests" // Skipping tests
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