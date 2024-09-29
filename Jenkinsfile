pipeline {
    agent any

    tools {
        jdk 'Java 22'
        dockerTool 'docker'
    }

    environment {
        REPO_URL = 'https://github.com/pipe1408/aerolinea-back.git'
        BRANCH_NAME = 'dev'
        DOCKERHUB_REPO = 'pipeba1408/arquitectura-aeropuerto-back'
        DOCKERHUB_CREDENTIALS_ID = '0445f049-d861-4258-83c4-b06c38944c28'
        GITHUB_CREDENTIALS_ID = 'github-pat' // Add your GitHub Token ID here
    }

    stages {
        stage('Clonar repositorio') {
            steps {
                withCredentials([string(credentialsId: GITHUB_CREDENTIALS_ID, variable: 'GITHUB_TOKEN')]) {
                    script {
                        githubNotify credentialsId: GITHUB_CREDENTIALS_ID, context: 'Clonar repositorio', status: 'PENDING'
                        try {
                            git branch: BRANCH_NAME, url: REPO_URL
                            githubNotify credentialsId: GITHUB_CREDENTIALS_ID, context: 'Clonar repositorio', status: 'SUCCESS'
                        } catch (Exception e) {
                            githubNotify credentialsId: GITHUB_CREDENTIALS_ID, context: 'Clonar repositorio', status: 'FAILURE'
                            error "Failed to clone repository: ${e.message}"
                        }
                    }
                }
            }
        }

        // Repeat for other stages as well
        stage('Build') {
            steps {
                withCredentials([string(credentialsId: GITHUB_CREDENTIALS_ID, variable: 'GITHUB_TOKEN')]) {
                    script {
                        githubNotify credentialsId: GITHUB_CREDENTIALS_ID, context: 'Build', status: 'PENDING'
                        try {
                            env.JAVA_HOME = tool name: 'Java 22'
                            sh "${env.JAVA_HOME}/bin/java -version"

