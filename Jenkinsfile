pipeline {
    agent any
    tools {
        maven 'Maven 3.9.6'
    }
    stages {
           stage('Build') 
        {
            steps{
                echo("build teh app")
            }
        }
        
        
        
        stage("Deploy to QA"){
            steps{
                echo("deploy to qa")
            }
        }
         
        stage('Regression automation Test') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    git 'https://github.com/rameshn3/opencart-ui-automation'
                    bat 'mvn -X clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng.xml'

                }
            }
        }
        stage('Publish Extent report') {
            steps {
                publishHTML([
                    allowMissing: false,
                    alwaysLinkToLastBuild: false,
                    keepAll: true,
                    reportDir: 'test-results',
                    reportFiles: 'TestExecutionReport.html',
                    reportName: 'HTML Extent Report',
                    reportTitles: ''
                ])
            }
        }
    }
}
