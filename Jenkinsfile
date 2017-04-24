pipeline {
    agent any
    stages {
        stage('checkout') {
            steps {
                checkout scm
            }
        }

        stage('clean') {
            steps {
                sh "./gradlew clean"
            }
        }

        stage('build') {
            steps {
                parallel(
                    "build": {
                        sh './gradlew build -Pprod'

                    },
                    "test": {
                        sh './gradlew test'

                    }
                )
            }
        }


    }
}
