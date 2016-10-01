node {
    def mvnHome
    stage('Preparation') { // for display purposes
        checkout scm
        mvnHome = tool 'm3'
    }
    stage('Build') {
        // Run the maven build
        if (isUnix()) {
            sh "'${mvnHome}/bin/mvn' test -Dwebtest.testng=google-search"
        } else {
            bat(/"${mvnHome}\bin\mvn" test -Dwebtest.testng=google-search/)
        }
    }
    stage('Results') {
        junit '**/target/surefire-reports/TEST-*.xml'
        archive 'target/*.jar'
    }
}