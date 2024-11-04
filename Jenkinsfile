pipeline {
    agent any

    stages {
        stage('GIT') {
            steps {
                echo "Getting project from Git"
                git 'https://github.com/bellalounaiheb/5SAE7-G3-Kaddem.git' // Your Git repository URL
            }
        }
        stage('MVN CLEAN') {
            steps {
                echo 'Running Maven clean'
                sh 'mvn clean -DskipTests' // Skipping tests
            }
        }
        stage('WN COMPILE') {
            steps {
                echo 'Compiling the project'
                sh 'mvn compile -DskipTests' // Skipping tests
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
        stage('MVN DEPLOY TO NEXUS') {
            steps {
                echo 'Deploying artifacts to Nexus'
                script {
                    // Deploy to Nexus using Maven, skipping tests
                    sh "mvn deploy -DskipTests"
                }
            }
        }
    }
}