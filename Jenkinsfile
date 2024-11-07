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

        stage('SonarQube Analysis') {
            steps {
                echo 'Running SonarQube Analysis'
                sh '''
                    mvn sonar:sonar \
                        -Dsonar.host.url=http://10.0.2.15:9000 \
                        -Dsonar.login=admin \
                        -Dsonar.password=Sonarqube12345#? \
                        -Dsonar.exclusions="src/main/java/tn/esprit/spring/kaddem/entities/Equipe.java,
                        src/main/java/tn/esprit/spring/kaddem/entities/DetailEquipe.java,
                        src/main/java/tn/esprit/spring/kaddem/entities/Etudiant.java,
                        src/main/java/tn/esprit/spring/kaddem/entities/Contrat.java,
                        src/main/java/tn/esprit/spring/kaddem/entities/Departement.java,
                        src/main/java/tn/esprit/spring/kaddem/controllers/ContratRestController.java,
                        src/main/java/tn/esprit/spring/kaddem/controllers/DepartementRestController.java,
                        src/main/java/tn/esprit/spring/kaddem/services/DepartementServiceImpl.java,
                        src/main/java/tn/esprit/spring/kaddem/controllers/EquipeRestController.java,
                        src/main/java/tn/esprit/spring/kaddem/services/EquipeServiceImpl.java,
                        src/main/java/tn/esprit/spring/kaddem/controllers/EtudiantRestController.java,
                        src/main/java/tn/esprit/spring/kaddem/services/EtudiantServiceImpl.java,
                        src/main/java/tn/esprit/spring/kaddem/KaddemApplication.java,
                        src/main/java/tn/esprit/spring/kaddem/entities/Niveau.java,
                        src/main/java/tn/esprit/spring/kaddem/entities/Option.java,
                        src/main/java/tn/esprit/spring/kaddem/entities/Universite.java,
                        src/main/java/tn/esprit/spring/kaddem/controllers/UniversiteRestController.java,
                        src/main/java/tn/esprit/spring/kaddem/services/UniversiteServiceImpl.java"
                '''
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
