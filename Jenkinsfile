pipeline {
	agent any

	tools {
		maven 'Maven3'
		jdk 'JDK17'
	}

	environment {
		SONARQUBE = credentials('sonar-token')
	}

	stages {
		stage('Checkout') {
			steps {
				checkout scm
			}
		}

		stage('Build & Test') {
			steps {
				bat 'mvn clean verify'
			}
			post {
				always {
					// Ruta exacta confirmada por ti
					junit 'target/surefire-reports/*.xml'
				}
			}
		}

		stage('Code Coverage (JaCoCo)') {
			steps {
				bat 'mvn jacoco:report'
			}
			post {
				always {
					publishHTML(target: [
						reportDir: 'target/site/jacoco',
						reportFiles: 'index.html',
						reportName: 'JaCoCo Coverage'
					])
				}
			}
		}

		stage('Static Analysis - SonarQube') {
			steps {
				script {
					catchError(buildResult: 'UNSTABLE', stageResult: 'FAILURE') {
						withSonarQubeEnv('SonarQubeServer') {
							bat "mvn sonar:sonar -Dsonar.login=${SONARQUBE}"
						}
					}
				}
			}
		}


		stage('Dependency Check') {
			steps {
				bat 'mvn org.owasp:dependency-check-maven:check'
			}
			post {
				always {
					publishHTML(target: [
						reportDir: 'target',
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
			archiveArtifacts artifacts: 'target/**/*.jar', fingerprint: true
		}
	}
}