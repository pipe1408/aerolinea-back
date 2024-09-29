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
        DOCKERHUB_CREDENTIALS_ID = '9294acd8-31fc-4d7c-b749-85cd8def1469'
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

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonar') {
                    sh "./gradlew sonar"
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    def image = docker.build("${DOCKERHUB_REPO}:${env.BUILD_NUMBER}")
                }
            }
        }

        stage('Login to DockerHub') {
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', DOCKER_CREDENTIALS_ID) {
                        echo 'Logged in to DockerHub'
                    }
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    def image = docker.image("${DOCKERHUB_REPO}:${env.BUILD_NUMBER}")
                    docker.withRegistry('https://index.docker.io/v1/', DOCKER_CREDENTIALS_ID) {
                        image.push()
                    }
                }
            }
        }
    }

    post {
        always {
            script {
                sh "docker rmi ${DOCKERHUB_REPO}:${env.BUILD_NUMBER} || true"
            }
        }
    }
}
