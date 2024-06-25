pipeline {
    agent {
        docker {
            image 'mcr.microsoft.com/playwright/java:v1.44.0-jammy'
            args '-v /var/run/docker.sock:/var/run/docker.sock'
        }
    }
    environment {
        MAVEN_OPTS = '-Dmaven.repo.local=.m2/repository'
    }
    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/rameshn3/opencart-ui-automation.git'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
    }
    post {
        always {
            archiveArtifacts artifacts: '**/target/*.jar', allowEmptyArchive: true
            junit 'target/surefire-reports/*.xml'
            cleanWs()
        }
    }
}
