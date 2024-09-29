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
    }

    stages {
        stage('Clonar repositorio') {
            steps {
                script {
                    githubNotify context: 'Clonar repositorio', status: 'PENDING'
                    try {
                        git branch: BRANCH_NAME, url: REPO_URL
                        githubNotify context: 'Clonar repositorio', status: 'SUCCESS'
                    } catch (Exception e) {
                        githubNotify context: 'Clonar repositorio', status: 'FAILURE'
                        error "Failed to clone repository: ${e.message}"
                    }
                }
            }
        }

        stage('Set permissions') {
            steps {
                script {
                    githubNotify context: 'Set permissions', status: 'PENDING'
                    try {
                        sh 'chmod +x gradlew'
                        githubNotify context: 'Set permissions', status: 'SUCCESS'
                    } catch (Exception e) {
                        githubNotify context: 'Set permissions', status: 'FAILURE'
                        error "Failed to set permissions: ${e.message}"
                    }
                }
            }
        }

        stage('Build') {
            steps {
                script {
                    githubNotify context: 'Build', status: 'PENDING'
                    try {
                        env.JAVA_HOME = tool name: 'Java 22'
                        sh "${env.JAVA_HOME}/bin/java -version"
                        sh './gradlew clean build'
                        githubNotify context: 'Build', status: 'SUCCESS'
                    } catch (Exception e) {
                        githubNotify context: 'Build', status: 'FAILURE'
                        error "Build failed: ${e.message}"
                    }
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                script {
                    githubNotify context: 'SonarQube Analysis', status: 'PENDING'
                    try {
                        withSonarQubeEnv('sonar') {
                            sh "./gradlew sonar"
                        }
                        githubNotify context: 'SonarQube Analysis', status: 'SUCCESS'
                    } catch (Exception e) {
                        githubNotify context: 'SonarQube Analysis', status: 'FAILURE'
                        error "SonarQube analysis failed: ${e.message}"
                    }
                }
            }
        }

        stage('Quality Gate') {
            steps {
                script {
                    githubNotify context: 'Quality Gate', status: 'PENDING'
                    try {
                        timeout(time: 5, unit: 'MINUTES') {
                            waitForQualityGate abortPipeline: true
                        }
                        githubNotify context: 'Quality Gate', status: 'SUCCESS'
                    } catch (Exception e) {
                        githubNotify context: 'Quality Gate', status: 'FAILURE'
                        error "Quality Gate failed: ${e.message}"
                    }
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    githubNotify context: 'Build Docker Image', status: 'PENDING'
                    try {
                        def image = docker.build("${DOCKERHUB_REPO}:${env.BUILD_NUMBER}")
                        githubNotify context: 'Build Docker Image', status: 'SUCCESS'
                    } catch (Exception e) {
                        githubNotify context: 'Build Docker Image', status: 'FAILURE'
                        error "Failed to build Docker image: ${e.message}"
                    }
                }
            }
        }

        stage('Login to DockerHub') {
            steps {
                script {
                    githubNotify context: 'Login to DockerHub', status: 'PENDING'
                    try {
                        docker.withRegistry('https://index.docker.io/v1/', env.DOCKERHUB_CREDENTIALS_ID) {
                            echo 'Logged in to DockerHub'
                        }
                        githubNotify context: 'Login to DockerHub', status: 'SUCCESS'
                    } catch (Exception e) {
                        githubNotify context: 'Login to DockerHub', status: 'FAILURE'
                        error "Failed to login to DockerHub: ${e.message}"
                    }
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    githubNotify context: 'Push Docker Image', status: 'PENDING'
                    try {
                        def image = docker.image("${DOCKERHUB_REPO}:${env.BUILD_NUMBER}")
                        docker.withRegistry('https://index.docker.io/v1/', env.DOCKERHUB_CREDENTIALS_ID) {
                            image.push()
                        }
                        githubNotify context: 'Push Docker Image', status: 'SUCCESS'
                    } catch (Exception e) {
                        githubNotify context: 'Push Docker Image', status: 'FAILURE'
                        error "Failed to push Docker image: ${e.message}"
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
