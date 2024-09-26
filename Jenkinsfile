pipeline {
    agent any

    stages {
        stage('Clonar repositorio') {
            steps {
                git branch: 'dev', url: 'https://github.com/pipe1408/aerolinea-back.git'
            }
        }

        stage('Build') {
            steps {
                sh 'sudo ./gradlew clean build'
            }
        }
    }
}
