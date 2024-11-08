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

        stage('Tests - JUnit/Mockito') {
            steps {
                echo 'Running Tests'
                sh 'mvn test'
            }
        }

        stage('Generate JaCoCo Report') {
            steps {
                echo 'Generating JaCoCo Report'
                sh 'mvn jacoco:report'
            }
        }

        stage('JaCoCo Coverage Report') {
            steps {
                echo 'Publishing JaCoCo Coverage Report'
                step([$class: 'JacocoPublisher',
                      execPattern: '**/target/jacoco.exec',
                      classPattern: '**/classes',
                      sourcePattern: '**/src',
                      exclusionPattern: '/target/**/,**/*Test,**/*_javassist/**'
                ])
            }
        }

        stage('SonarQube') {
            steps {
                echo 'Running SonarQube Analysis'
                sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=Sonarqube12345#'
            }
        }

        stage('Deploy to Nexus') {
            steps {
                echo 'Deploying to Nexus Repository'
                sh 'mvn deploy -Dmaven.test.skip=true'
            }
        }

        stage("Docker Build") {
            steps {
                echo "Building Docker image..."
                sh "docker build -t malekkh/malekkhelil-5sae7-g3 ."
                echo 'Docker image built successfully!'
            }
        }

        stage('Pushing to DockerHub') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'
                    sh 'docker push malekkh/malekkhelil-5sae7-g3'
                }
            }
        }

        stage("Stopping the Containers") {
            steps {
                sh "docker-compose down"
            }
        }

        stage('Deploy with Docker Compose') {
            steps {
                echo 'Starting Docker containers...'
                sh 'docker-compose up -d'
                echo 'Containers started!'
            }
        }
    }


}
