// Jenkinsfile (Declarative Pipeline)
pipeline {
  agent any
  stages {
    stage('Checkout') {
      steps {
        git url: 'https://github.com/your-org/your-repo.git', branch: 'main'
      }
    }
    stage('Build') {
      steps {
        echo 'Building the project…'
        // sh 'mvn clean package'   // for example
      }
    }
    stage('Test') {
      steps {
        echo 'Running tests…'
        // sh 'mvn test'
      }
    }
  }
}