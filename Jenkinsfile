pipeline {
    options {
        disableConcurrentBuilds()
    }

    agent any

    stages {
        stage('Build') {
            agent {
                docker {
                    image 'docker.dvladir.work/library/maven:3.8.6-openjdk-18'
                    label 'maven'
                    args '--net=host'
                    reuseNode true
                }
            }
            steps {
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
        stage('Prepare DB') {
            agent {
                docker {
                    image 'docker.dvladir.work/flyway/flyway:7.14.0-alpine'
                    label 'flyway'
                    args '-v .sql:/flyway:sql --entrypoint=\'\' --net=host'
                    reuseNode true
                }
            }
            steps {
                configFileProvider([configFile(fileId: 'deploy-env-flyway', targetLocation: './flyway.config')]) {
                    sh 'flyway -configFiles=./flyway.config -connectRetries=60 migrate'
                }
            }
        }
        stage('Deploy') {
            steps {
                sh 'echo Branch name: ${BRANCH_NAME}'
                sh 'docker build --t docker-push.dvladir.work/partners-api:latest --file Dockerfile.deploy .'
                sh 'docker push docker-push.dvladir.work/parthers-api:latest'
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}