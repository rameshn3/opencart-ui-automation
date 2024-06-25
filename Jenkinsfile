pipeline {
    agent any
    tools {
        maven 'Maven 3.9.6'
    }
    stages {
           stage('Build') 
        {
            steps
            {
                 git 'https://github.com/jglick/simple-maven-project-with-tests.git'
                 sh "mvn -Dmaven.test.failure.ignore=true clean package"
            }
            post 
            {
                success
                {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
                }
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
                    git 'https://github.com/rameshn3/opencart-ui-automation.git'
                    bat 'mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng.xml'
                }
            }
        }
        stage('Publish Extent report') {
            steps {
                publishHTML([
                    allowMissing: false,
                    alwaysLinkToLastBuild: false,
                    keepAll: true,
                    reportDir: 'build',
                    reportFiles: 'TestExecutionReport.html',
                    reportName: 'HTML Extent Report',
                    reportTitles: ''
                ])
            }
        }
    }
}
