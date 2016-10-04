node {
    def mvnHome
    stage('Preparation') {
        checkout scm
        mvnHome = tool 'm3'
    }
    stage('Build') {
        if (isUnix()) {
            sh "'${mvnHome}/bin/mvn' test -Dwebtest.testng=all"
        } else {
            bat(/"${mvnHome}\bin\mvn" test -Dwebtest.testng=all/)
        }
    }
    stage('Results') {
        junit '**/target/surefire-reports/TEST-*.xml'
        publishHTML([allowMissing         : false,
                     alwaysLinkToLastBuild: false,
                     keepAll              : true,
                     reportDir            : 'target\\surefire-reports\\html\\',
                     reportFiles          : 'index.html',
                     reportName           : 'ReportNG'])
    }
}