	node {
		stage('Checkout') {
			checkout scm
		}

		stage('Build & Test') {
			bat 'mvn clean verify'
			junit 'target/surefire-reports/*.xml'
		}

		stage('Code Coverage (JaCoCo)') {
			bat 'mvn jacoco:report'
			publishHTML(target: [
				reportDir: 'target/site/jacoco',
				reportFiles: 'index.html',
				reportName: 'JaCoCo Coverage'
			])
		}

		stage('Static Analysis - SonarQube') {
			withSonarQubeEnv('SonarQubeServer') {
				withCredentials([string(credentialsId: 'sonar-token', variable: 'SONAR_TOKEN')]) {
					bat "mvn sonar:sonar -Dsonar.login=%SONAR_TOKEN%"
				}
			}
		}

		stage('Dependency Check') {
			bat 'mvn org.owasp:dependency-check-maven:check'
			publishHTML(target: [
				reportDir: 'target',
				reportFiles: 'dependency-check-report.html',
				reportName: 'OWASP Dependency Check'
			])
		}

		stage('Quality Gate') {
			timeout(time: 1, unit: 'MINUTES') {
				waitForQualityGate abortPipeline: true
			}
		}

		stage('Archive Artifacts') {
			archiveArtifacts artifacts: 'target/**/*.jar', fingerprint: true
		}
	}