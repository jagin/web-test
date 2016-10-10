node('windows') {
    def mvnHome
    stage('Preparation') {
        checkout scm
        mvnHome = tool 'm3'
    }
    stage('Build') {
            //running on windows to check IE
            bat(/"${mvnHome}\bin\mvn" test -Dwebtest.testng=all/)
        }
    stage('Results') {
        junit keepLongStdio: true, testResults: '**/target/surefire-reports/TEST-*.xml'
        publishHTML([allowMissing         : false,
                     alwaysLinkToLastBuild: false,
                     keepAll              : true,
                     reportDir            : 'target\\surefire-reports\\html\\',
                     reportFiles          : 'index.html',
                     reportName           : 'ReportNG'])
    }
}