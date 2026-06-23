pipeline {
    agent any

    tools {
        maven 'maven-3'
        jdk 'jdk17'
    }

    environment {
        DOCKER_IMAGE = 'courier-backend:latest'
        K8S_DEPLOYMENT = 'courier-backend'
        K8S_NAMESPACE = 'default'
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build with Maven') {
            steps {
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Run Tests') {
            steps {
                bat 'mvn test'
            }
        }

        stage('Build Docker Image') {
            steps {
                bat 'docker build -t %DOCKER_IMAGE% .'
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                bat 'kubectl set image deployment/%K8S_DEPLOYMENT% backend=%DOCKER_IMAGE% --namespace=%K8S_NAMESPACE%'
            }
        }
    }

    post {
        success {
            echo 'Build and deployment successful!'
        }
        failure {
            echo 'Build or deployment failed!'
        }
    }
}
