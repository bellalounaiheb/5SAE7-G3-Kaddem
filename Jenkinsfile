pipeline {
    agent any
    tools {
        jdk 'JAVA_HOME'
        maven 'M2_HOME'
    }
    stages {
            stage('Checkout') {
                steps {
                    retry(3) { // Retry up to 3 times in case of failure
                        checkout([$class: 'GitSCM',
                                  branches: [[name: 'refs/heads/OnsHAMZAOUI-5SAE7-G3']],
                                  userRemoteConfigs: [[url: 'https://github.com/bellalounaiheb/5SAE7-G3-Kaddem.git']],
                                  extensions: [
                                      [$class: 'CheckoutOption', timeout: 60], // Increased timeout to 60 minutes
                                      [$class: 'CloneOption', depth: 1, shallow: true] // Shallow clone with depth of 1
                                  ]
                        ])
                    }
                }
            }

    /* stages {
        stage('GIT') {
            steps {
                git branch: 'OnsHAMZAOUI-5SAE7-G3',
                    url: 'https://github.com/bellalounaiheb/5SAE7-G3-Kaddem.git'
            }
        } */
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