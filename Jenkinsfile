pipeline {
    options {
        disableConcurrentBuilds()
    }

    agent any

    stages {
        stage('Build') {
            agent {
                docker {
                    image 'maven:3.8.6-openjdk-18'
                    args '--net=host'
                    reuseNode true
                }
            }
            steps {
                sh 'mvn clean'
                sh 'mvn deploy'
            }
        }
        stage('Deploy') {
            environment {
                DEPLOY_HOST = credentials('deploy-host')
                DEPLOY_PASS = credentials('deploy-pass')
            }
            steps {
                sh 'DOCKER_BUILDKIT=1 docker build --output type=tar,dest=partners-api-java.tar --file Dockerfile.deploy .'
                sh 'gzip partners-api-java.tar'
                sh 'echo ${DEPLOY_PASS} >> pass'
                sh 'sshpass -Ppassphrase -f ./pass rsync ./partners-api-java.tar.gz ${DEPLOY_HOST}:~/partners-deploy/partners-api-java.tar.gz'
                // sh 'sshpass -Ppassphrase -f ./pass ssh ${DEPLOY_HOST} cd \\~/partners-deploy \\&\\& ./scripts/recreate.sh ./partners-api.tar.gz dvladir:partners-api api'
                sh 'rm ./pass'
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}