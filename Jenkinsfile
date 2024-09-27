pipeline {
    agent any

    tools {
            jdk 'Java 22'
        }

    environment {
        REPO_URL = 'https://github.com/pipe1408/aerolinea-back.git'
        BRANCH_NAME = 'dev'
    }

    stages {
        stage('Clonar repositorio') {
            steps {
                script {
                    try {
                        git branch: BRANCH_NAME, url: REPO_URL
                    } catch (Exception e) {
                        error "Failed to clone repository: ${e.message}"
                    }
                }
            }
        }

        stage('Set permissions') {
            steps {
                sh 'chmod +x gradlew'
            }
        }

        stage('Build') {
            steps {
                script {
                    env.JAVA_HOME = tool name: 'Java 22'
                    sh "${env.JAVA_HOME}/bin/java -version"
                }
                sh './gradlew clean build'
            }
        }
    }
}
