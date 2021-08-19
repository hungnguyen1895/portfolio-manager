def projectName = 'portfoliomanager'
def version = "0.0.${currentBuild.number}"
def dockerImageTag = "${projectName}:${version}"

pipeline {
  agent any

  stages {
     stage('Build docker image') {
          // this stage also builds and tests the Java project using Maven
          environment {
             MYSQL_CREDS = credentials('MySQLCreds')
          }
          steps {
            sh "docker build --build-arg USERNAME=${MYSQL_CREDS_USR} --build-arg PASSWORD=${MYSQL_CREDS_PSW} -t ${dockerImageTag} . "
          }
      }
    stage('Deploy Container To Openshift') {
      environment {
           OPENSHIFT_CREDS = credentials('openshiftCreds')
          }
      steps {
        sh "oc login -u ${OPENSHIFT_CREDS_USR} -p ${OPENSHIFT_CREDS_PSW} --insecure-skip-tls-verify"
        sh "oc project ${projectName} || oc new-project ${projectName}"
        sh "oc delete all --selector app=${projectName} || echo 'Unable to delete all previous openshift resources'"
        sh "oc new-app ${dockerImageTag} -l version=${version}"
        sh "oc expose svc/${projectName}"
      }
    }
  }
}