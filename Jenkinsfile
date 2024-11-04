pipeline {
    agent any
    tools {
        jdk 'JAVA_HOME'
        maven 'M2_HOME'
    }
    environment {
            // Set the SonarQube token as an environment variable for security
            SONAR_TOKEN = 'squ_5574cbc60ea6c6c0e9908b0065b9b0d6c0bb43a6' // Use your actual token here
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

        stage('SonarQube') {
            steps {
                sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=Sonarqube12345# -Dmaven.test.skip=true'
            }
        }

        stage('Nexus') {
           steps {
              sh 'mvn deploy -Dmaven.test.skip=true'
           }
        }
    }
}