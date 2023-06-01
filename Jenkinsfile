pipeline {
    agent any
    
    environment {
        JAVA_HOME = '/usr/lib/jvm/java-17-openjdk-amd64/bin/java'
    }
    
    stages {
        stage('Checkout') {
            steps {
                // Checkout the repository
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/kasiemobi/Test_App.git']])
            }
        }
        
        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }
        
        stage('Start PostgreSQL Database') {
            steps {
                sh 'docker-compose -f docker-compose up -d db'
            }
        }
        
        stage('Migrate Database Schema') {
            steps {
                sh 'java -jar target/dropwizard-realworld-example-app-1.0-SNAPSHOT.jar db migrate config.yml'
            }
        }
        
        stage('Start Application') {
            steps {
                sh 'java -jar target/dropwizard-realworld-example-app-1.0-SNAPSHOT.jar server config.yml &'
            }
        }
        
        stage('Verify Application') {
            steps {
                script {
                    def metricsUrl = 'http://localhost:8081'
                    def apiBaseUrl = 'http://localhost:8080/api/'
                    
                    echo "Checking metrics/health checks at ${metricsUrl}"
                    echo "Checking APIs at ${apiBaseUrl}"
                }
            }
        }
        
        stage('Sonar Code Quality Check') {
            steps {
                sh 'docker-compose -f sonar.yml up -d'
                sh 'mvn clean install sonar:sonar -Dsonar.host.url=http://localhost:9001'
            }
        }
    }
}
