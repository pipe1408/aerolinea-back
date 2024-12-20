void setBuildStatus(String message, String state) {
  step([
      $class: "GitHubCommitStatusSetter",
      reposSource: [$class: "ManuallyEnteredRepositorySource", url: "https://github.com/pipe1408/aerolinea-back"],
      contextSource: [$class: "ManuallyEnteredCommitContextSource", context: "ci/jenkins/build-status"],
      errorHandlers: [[$class: "ChangingBuildStatusErrorHandler", result: "UNSTABLE"]],
      statusResultSource: [ $class: "ConditionalStatusResultSource", results: [[$class: "AnyBuildResult", message: message, state: state]] ]
  ]);
}

pipeline {
    agent any

    tools {
        jdk 'JDK22'
        dockerTool 'docker'
    }

    environment {
        DOCKERHUB_REPO = 'pipeba1408/arquitectura-aeropuerto-back'
        DOCKERHUB_CREDENTIALS_ID = 'eb0aab5d-b7b8-496b-bed6-fb5aedf23033'
    }

    stages {
        stage('Clonar repositorio') {
            steps {
                setBuildStatus("Build in progress", "PENDING");
                script {
                    try {
                        checkout scm
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
                    env.JAVA_HOME = tool name: 'JDK22'
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
                    def image = docker.build("${DOCKERHUB_REPO}:${GIT_BRANCH}-${env.BUILD_NUMBER}")
                    sh "docker images"
                }
            }
        }

        stage ('Trivy verification') {
            steps {
                script {
                    sh """
                    docker run -u root -v $HOME/Library/Caches:/root/.cache/ -v /var/run/docker.sock:/var/run/docker.sock --name trivy_scan aquasec/trivy image --scanners vuln --severity CRITICAL --exit-code 1 ${DOCKERHUB_REPO}:${GIT_BRANCH}-${env.BUILD_NUMBER}
                    """
                }
            }
        }

        stage('Login to DockerHub') {
            when {
                branch 'master'
            }
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', env.DOCKERHUB_CREDENTIALS_ID) {
                        echo 'Logged in to DockerHub'
                    }
                }
            }
        }

        stage('Push Docker Image') {
            when {
                branch 'master'
            }
            steps {
                script {
                    def image = docker.image("${DOCKERHUB_REPO}:${GIT_BRANCH}-${env.BUILD_NUMBER}")
                    docker.withRegistry('https://index.docker.io/v1/', env.DOCKERHUB_CREDENTIALS_ID) {
                        image.push()
                    }
                }
            }
        }
    }

    post {
        always {
            script {
                sh "docker rmi ${DOCKERHUB_REPO}:${GIT_BRANCH}-${env.BUILD_NUMBER} || true"
                sh 'docker rm -f trivy_scan || true'
            }
        }
        success {
            setBuildStatus("Build succeeded", "SUCCESS");
        }
        failure {
            setBuildStatus("Build failed", "FAILURE");
        }
    }
}
