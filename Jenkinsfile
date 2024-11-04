pipeline {
    agent { label 'slave01' }
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
                bat 'mvn clean compile'
            }
        }

         stage('SonarQube') {
             steps {
                bat 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=Mohamedali_22 -Dmaven.test.skip=true'
           }
         }

        stage('Nexus') {
            steps {
                bat 'mvn deploy -Dmaven.test.skip=true'
            }
        }
    }
}