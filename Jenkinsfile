pipeline {
    options {
        disableConcurrentBuilds()
    }

    agent any

    environment {
        BRANCH="${BRANCH_NAME.replaceAll('feat/', '').toLowerCase()}"
    }

    stages {
        stage('Build') {
            agent {
                docker {
                    image 'docker.dvladir.work/library/maven:3.8.6-openjdk-18'
                    args '--net=host'
                    reuseNode true
                }
            }
            steps {
                sh 'echo BRANCH: $BRANCH'
                withCredentials([
                    file(credentialsId: 'jvm.config', variable: 'JVM_CONFIG'),
                    file(credentialsId: 'dvladir.work.jks', variable: 'JKS_STORE'),
                ]) {
                    sh 'mkdir .mvn'
                    sh 'mv $JVM_CONFIG .mvn/jvm.config'
                    sh 'mv $JKS_STORE .mvn/dvladir.work.jks'
                }
                configFileProvider([configFile(fileId: 'maven-local', variable: 'MAVEN_SETTINGS')]) {
                    sh 'mvn -s $MAVEN_SETTINGS clean deploy'
                }
                sh 'rm -rf .mvn'
            }
        }
        stage('Deploy') {
            steps {
                sh 'docker build --tag docker-push.dvladir.work/partners/$BRANCH/partners-api:latest --file Dockerfile.deploy .'
                sh 'docker push docker-push.dvladir.work/partners/$BRANCH/partners-api:latest'
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}