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
                DB_CREDS=credentials('db_creds')
                DB_URL="jdbc:postgresql://db.dvladir.work:5432/partners_db_test"
            }
            steps {
                sh 'echo $DB_URL'
                sh 'docker run --net=host --rm docker.dvladir.work/flyway/flyway:8.5.1 version'
                sh 'docker run --net=host --rm -v $WORKSPACE/sql:/flyway/sql docker.dvladir.work/flyway/flyway:8.5.1 -user=$DB_CREDS_USR -password=$DB_CREDS_PSW -url=$DB_URL migrate'
                sh 'docker run --net=host --rm -v $WORKSPACE/sql:/flyway/sql docker.dvladir.work/flyway/flyway:8.5.1 -user=$DB_CREDS_USR -password=$DB_CREDS_PSW -url=$DB_URL validate'
                sh 'docker run --net=host --rm -v $WORKSPACE/sql:/flyway/sql docker.dvladir.work/flyway/flyway:8.5.1 -user=$DB_CREDS_USR -password=$DB_CREDS_PSW -url=$DB_URL info'
            }
        }
        stage('Deploy') {
            steps {
                sh 'echo Branch name: ${BRANCH_NAME}'
                sh 'docker build --tag docker-push.dvladir.work/partners-api:latest --file Dockerfile.deploy .'
                sh 'docker push docker-push.dvladir.work/partners-api:latest'
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}