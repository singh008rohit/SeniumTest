pipeline {
    agent any

    stages {
        stage('Parallel Cross-Browser Testing') {
            parallel {
                stage('Test on Chrome') {
                    agent {
                        label 'chrome-node'
                    }
                    steps {
                        echo 'Running tests on Chrome...'
                        sh 'mvn test -Dbrowser=chrome'
                    }
                }

                stage('Test on Firefox') {
                    agent {
                        label 'firefox-node'
                    }
                    steps {
                        echo 'Running tests on Firefox...'
                        sh 'mvn test -Dbrowser=firefox'
                    }
                }

                stage('Test on Edge') {
                    agent {
                        label 'edge-node'
                    }
                    steps {
                        echo 'Running tests on Edge...'
                        sh 'mvn test -Dbrowser=edge'
                    }
                }
            }
        }
    }

    post {
        always {
            echo 'Tests completed on all browsers.'
        }
    }
}