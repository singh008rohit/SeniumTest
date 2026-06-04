pipeline {

    agent any

    tools {
        jdk 'JDK17'
        maven 'Maven3'
    }

    options {
        timestamps()
        timeout(time: 60, unit: 'MINUTES')
        buildDiscarder(logRotator(
                numToKeepStr: '20',
                artifactNumToKeepStr: '10'
        ))
    }

    parameters {

        choice(
            name: 'BROWSER',
            choices: ['all', 'chrome', 'firefox', 'edge'],
            description: 'Browser to run UI tests on'
        )

        choice(
            name: 'ENV',
            choices: ['QA', 'STAGE', 'PRODUCTION'],
            description: 'Target environment'
        )

        booleanParam(
            name: 'USE_MOCK',
            defaultValue: false,
            description: 'Run API tests against WireMock'
        )

        string(
            name: 'THREAD_COUNT',
            defaultValue: '2',
            description: 'Parallel execution thread count'
        )
    }

    environment {
        MAVEN_OPTS = '-Xmx2048m'
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm

                echo "Branch : ${env.BRANCH_NAME}"
                echo "Build  : ${env.BUILD_NUMBER}"
                echo "Browser: ${params.BROWSER}"
                echo "Env    : ${params.ENV}"
            }
        }

        stage('Build') {
            steps {
                sh '''
                    mvn clean test-compile \
                    -DskipTests=true
                '''
            }
        }

        stage('Execute Tests') {

            parallel {

                stage('Chrome') {

                    when {
                        expression {
                            params.BROWSER == 'chrome' ||
                            params.BROWSER == 'all'
                        }
                    }

                    steps {

                        sh """
                            mvn test \
                              -Dbrowser=chrome \
                              -Denv=${params.ENV} \
                              -DuseMock=${params.USE_MOCK} \
                              -Dsurefire.threadCount=${params.THREAD_COUNT}
                        """
                    }
                }

                stage('Firefox') {

                    when {
                        expression {
                            params.BROWSER == 'firefox' ||
                            params.BROWSER == 'all'
                        }
                    }

                    steps {

                        sh """
                            mvn test \
                              -Dbrowser=firefox \
                              -Denv=${params.ENV} \
                              -DuseMock=${params.USE_MOCK} \
                              -Dsurefire.threadCount=${params.THREAD_COUNT}
                        """
                    }
                }

                stage('Edge') {

                    when {
                        expression {
                            params.BROWSER == 'edge' ||
                            params.BROWSER == 'all'
                        }
                    }

                    steps {

                        sh """
                            mvn test \
                              -Dbrowser=edge \
                              -Denv=${params.ENV} \
                              -DuseMock=${params.USE_MOCK} \
                              -Dsurefire.threadCount=${params.THREAD_COUNT}
                        """
                    }
                }
            }
        }
    }

    post {

        always {

            echo "Publishing reports..."

            testNG(
                reportFilenamePattern: '**/surefire-reports/testng-results.xml'
            )

            publishHTML(target: [
                allowMissing          : true,
                alwaysLinkToLastBuild : true,
                keepAll               : true,
                reportDir             : 'ExtentReports',
                reportFiles           : '**/*.html',
                reportName            : 'Extent Test Report'
            ])

            archiveArtifacts(
                artifacts: '''
                    logs/**/*.log,
                    ExtentReports/**/*.html
                ''',
                allowEmptyArchive: true
            )
        }

        success {
            echo "Build ${env.BUILD_NUMBER} completed successfully."
        }

        unstable {
            echo "Build ${env.BUILD_NUMBER} is unstable."
        }

        failure {
            echo "Build ${env.BUILD_NUMBER} failed."
        }

        cleanup {
            cleanWs()
        }
    }
}