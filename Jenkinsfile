#!/usr/bin/env groovy

node {
    
    stage('Checkout') {
        checkout scm
    }

    stage('Test') {
       sh './gradlew test || true'
    }

    stage('Build') {
        try {
            sh './gradlew build -x test'
        } catch(e) {
            mail subject: "Jenkins Job '${env.JOB_NAME}' (${env.BUILD_NUMBER}) failed with ${e.message}",
                to: 'yonghui.park@kt.com',
                body: "Please go to $env.BUILD_URL."
        }
    }

    stage('Archive') {
        parallel (
            "Archive Artifacts" : {
                archiveArtifacts artifacts: '**/build/libs/*.war', fingerprint: true
            },
            "Docker Image Push" : {
                sh './gradlew dockerPush'
            }
        )
    }

    stage('Deploy') {
        sh 'kubectl apply --namespace=development -f deployment.yaml'
       
    }
}
