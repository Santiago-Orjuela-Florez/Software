pipeline {
	agent any

	tools {
		maven 'Maven3'   // nombre configurado en Jenkins
		jdk 'JDK17'      // nombre configurado en Jenkins
	}

	environment {
		SONARQUBE = credentials('sonar-token') // ID de la credencial en Jenkins
		PROJECT_DIR = 'hotel-challenge-master' // carpeta donde está el pom.xml
	}

	stages {
		stage('Checkout') {
			steps {
				checkout scm
			}
		}

		stage('Build & Test') {
			steps {
				bat "cd %PROJECT_DIR% && mvn clean verify"
			}
			post {
				always {
					junit '**/%PROJECT_DIR%/target/surefire-reports/*.xml'
				}
			}
		}

		stage('Code Coverage (JaCoCo)') {
			steps {
				bat "cd %PROJECT_DIR% && mvn jacoco:report"
			}
			post {
				always {
					publishHTML(target: [
						reportDir: "${env.PROJECT_DIR}/target/site/jacoco",
						reportFiles: 'index.html',
						reportName: 'JaCoCo Coverage'
					])
				}
			}
		}

		stage('Static Analysis - SonarQube') {
			steps {
				withSonarQubeEnv('SonarQubeServer') {
					bat "cd %PROJECT_DIR% && mvn sonar:sonar -Dsonar.login=${SONARQUBE}"
				}
			}
		}

		stage('Dependency Check') {
			steps {
				bat "cd %PROJECT_DIR% && mvn org.owasp:dependency-check-maven:check"
			}
			post {
				always {
					publishHTML(target: [
						reportDir: "${env.PROJECT_DIR}/target",
						reportFiles: 'dependency-check-report.html',
						reportName: 'OWASP Dependency Check'
					])
				}
			}
		}

		stage('Quality Gate') {
			steps {
				timeout(time: 1, unit: 'MINUTES') {
					waitForQualityGate abortPipeline: true
				}
			}
		}
	}

	post {
		always {
			archiveArtifacts artifacts: "${env.PROJECT_DIR}/target/**/*.jar", fingerprint: true
		}
	}
}