pipeline {
    agent any

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

        stage('Build') {
            steps {
                script {
                    try {
                        sh './gradlew clean build'
                    } catch (Exception e) {
                        error "Build failed: ${e.message}"
                    }
                }
            }
        }
    }
}
