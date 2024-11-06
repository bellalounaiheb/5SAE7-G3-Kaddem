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


              stage('MVN CLEAN'){
                  steps{
                      sh 'mvn clean';
                  }
              }
              stage('MVN COMPILE'){
                  steps{
                      sh 'mvn compile';
                  }
              }
      stage('Tests - Mockito/JUnit') {
            steps {
                sh 'mvn test'
            }
        }
              stage('JaCoCo Reports') {
              steps {
                  sh 'mvn jacoco:report'
              }
          }
              stage("Build") {
                 steps {
                  sh 'mvn install -DskipTests=true'
                  }
              }



        stage('SonarQube') {
            steps {
                sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=Sonarqube12345# -Dmaven.test.skip=true'
           }
         }

        stage('Nexus Deployment') {
                  steps {
                      sh 'mvn deploy -Dmaven.test.skip=true'
                  }
              }
          }
      }
