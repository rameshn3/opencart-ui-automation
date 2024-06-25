pipeline {
    agent any
    tools {
        maven 'Maven 3.9.6'
    }
    stages {
        stage('Checkout') {
            steps {
                script {
                    def branch = 'main'
                    checkout([
                        $class: 'GitSCM',
                        branches: [[name: "*/${branch}"]],
                        doGenerateSubmoduleConfigurations: false,
                        extensions: [],
                        userRemoteConfigs: [[url: 'https://github.com/rameshn3/opencart-ui-automation.git']]
                    ])
                }
            }
        }
        stage('Build') {
            steps {
                sh "mvn -Dmaven.test.failure.ignore=true clean package"
            }
            post {
                success {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
                }
            }
        }
        stage('Deploy to QA') {
            steps {
                echo "Deploy to QA"
            }
        }
        stage('Regression Automation Test') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    script {
                        def branch = 'master'
                        checkout([
                            $class: 'GitSCM',
                            branches: [[name: "*/${branch}"]],
                            doGenerateSubmoduleConfigurations: false,
                            extensions: [],
                            userRemoteConfigs: [[url: 'https://github.com/rameshn3/opencart-ui-automation.git']]
                        ])
                    }
                    sh "mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng.xml"
                }
            }
        }
        stage('Publish Extent Report') {
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
