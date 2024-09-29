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

        stage('Scan with Trivy') {
            steps {
                script {
                    // Run Trivy to scan the built image and save the report
                    sh '''
                    docker run --rm \
                      -v /var/run/docker.sock:/var/run/docker.sock \
                      -v /var/jenkins_home/workspace/aerolinea-back:/results \
                      aquasec/trivy image --quiet --exit-code 1 --format json --output /results/trivy-report.json ${DOCKERHUB_REPO}:${env.BUILD_NUMBER}
                    '''
                }
            }
        }

        stage('Check Vulnerabilities') {
            steps {
                script {
                    // Load the Trivy report
                    def report = readJSON file: 'trivy-report.json'
                    def criticalVulns = report.Vulnerabilities.findAll { it.Severity == 'CRITICAL' }
                    def highVulns = report.Vulnerabilities.findAll { it.Severity == 'HIGH' }

                    // Check if there are any critical or high vulnerabilities
                    if (criticalVulns.size() > 0 || highVulns.size() > 0) {
                        error "Pipeline failed due to critical or high vulnerabilities: " +
                              "${criticalVulns.size()} critical and ${highVulns.size()} high vulnerabilities found."
                    } else {
                        echo "No critical or high vulnerabilities found."
                    }
                }
            }
        }

        stage('Publish Results') {
            steps {
                // Optionally publish the scan results (e.g., using the archiveArtifacts plugin)
                archiveArtifacts artifacts: 'trivy-report.json', fingerprint: true
            }
        }

        stage('Login to DockerHub') {
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', env.DOCKERHUB_CREDENTIALS_ID) {
                        echo 'Logged in to DockerHub'
                    }
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    def image = docker.image("${DOCKERHUB_REPO}:${env.BUILD_NUMBER}")
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
                sh "docker rmi ${DOCKERHUB_REPO}:${env.BUILD_NUMBER} || true"
            }
        }
    }
}
