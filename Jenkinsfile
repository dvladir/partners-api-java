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
            environment {
                DEPLOY_HOST = credentials('deploy-host')
                DEPLOY_PASS = credentials('deploy-pass')
            }
            steps {
                sh 'echo ${DEPLOY_PASS} >> pass'
                sh 'sshpass -Ppassphrase -f ./pass rsync -rv ./sql/ ${DEPLOY_HOST}:~/partners-deploy/sql'
                sh 'sshpass -Ppassphrase -f ./pass ssh ${DEPLOY_HOST} cd \\~/partners-deploy \\&\\& ./scripts/migrate.sh'
                sh 'rm ./pass'
            }
        }
        stage('Deploy') {
            environment {
                DEPLOY_HOST = credentials('deploy-host')
                DEPLOY_PASS = credentials('deploy-pass')
            }
            steps {
                sh 'DOCKER_BUILDKIT=1 docker build --output type=tar,dest=partners-api.tar --file Dockerfile.deploy .'
                sh 'gzip partners-api.tar'
                sh 'echo ${DEPLOY_PASS} >> pass'
                sh 'sshpass -Ppassphrase -f ./pass rsync ./partners-api.tar.gz ${DEPLOY_HOST}:~/partners-deploy/partners-api.tar.gz'
                sh 'sshpass -Ppassphrase -f ./pass ssh ${DEPLOY_HOST} cd \\~/partners-deploy \\&\\& ./scripts/recreate.sh ./partners-api.tar.gz dvladir:partners-api api'
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