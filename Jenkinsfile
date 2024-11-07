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

        stage('Build Package') {
            steps {
                echo 'Running Maven Package'
                sh 'mvn package'
            }
        }

stage('Tests - JUnit/Mockito') {
    steps {
        echo 'Running Tests'
        sh 'mvn test'
    }
}



    stage('SonarQube') {
              steps {
                  sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=Sonarqube12345# -Dmaven.test.skip=true'
              }
          }

stage('Deploy to Nexus') {
    steps {
        echo 'Deploying to Nexus Repository'
        sh 'mvn clean deploy -DskipTests'
    }
}

    }
}
