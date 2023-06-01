pipeline {
    agent any
    
    stages {
        stage('Checkout') {
            steps {
                // Checkout the repository
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/kasiemobi/Test_App.git']])
            }
        }
        
        stage('Build') {
            steps {
                // Build the Java application
                sh 'mvn clean install'
            }
        }
        
        stage('Test') {
            steps {
                // Run tests
                sh 'mvn test'
            }
        }
        
        stage('Build Docker Image') {
            steps {
                // Build the Docker image
                script {
                    docker.build('my-image-name', '-f Dockerfile .')
                }
            }
        }
        
        stage('Deploy') {
            steps {
                // Deploy the application
                echo 'Deploying application'
            }
        }
    }
}
