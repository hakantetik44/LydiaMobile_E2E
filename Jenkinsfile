pipeline {
  agent any

  parameters {
    choice(name: 'PLATFORM', choices: ['android','ios','web'], description: 'Platform to test')
  }

  options {
    timestamps()
    timeout(time: 90, unit: 'MINUTES')
  }

  stages {
    stage('Prepare') {
      steps {
        echo "Preparing environment for platform: ${params.PLATFORM}"
        sh 'java -version || true'

        script {
          // Set Maven path from Jenkins tool installation
          env.PATH = "${tool 'maven'}/bin:${env.PATH}"

          // Add npm global bin to PATH (for appium)
          def npmGlobalBin = sh(returnStdout: true, script: 'npm config get prefix 2>/dev/null || echo "/usr/local"').trim()
          env.PATH = "${npmGlobalBin}/bin:${env.PATH}"

          echo "Updated PATH: ${env.PATH}"
        }

        sh 'mvn -v'
        sh 'node -v || echo "Node.js not found"'
        sh 'npm -v || echo "npm not found"'
      }
    }

    stage('Setup Appium') {
      when {
        expression { params.PLATFORM != 'web' }
      }
      steps {
        script {
          echo "🔧 Checking Appium installation..."

          def appiumExists = sh(returnStatus: true, script: 'which appium >/dev/null 2>&1') == 0

          if (!appiumExists) {
            echo "📦 Installing Appium globally..."
            sh 'npm install -g appium@2.11.5 || true'
            sh 'sleep 2'
          } else {
            echo "✅ Appium already installed"
            sh 'appium --version'
          }

          // Install platform-specific drivers
          def p = params.PLATFORM.toLowerCase()
          if (p == 'ios') {
            echo "📱 Installing XCUITest driver..."
            sh 'appium driver install xcuitest || true'
          } else if (p == 'android') {
            echo "🤖 Installing UiAutomator2 driver..."
            sh 'appium driver install uiautomator2 || true'
          }

          sh 'appium driver list --installed'
        }
      }
    }

    stage('Start Appium Server') {
      when {
        expression { params.PLATFORM != 'web' }
      }
      steps {
        script {
          echo "🚀 Starting Appium server on port 4723..."

          // Kill any existing Appium process
          sh 'pkill -f appium || true'
          sh 'sleep 2'

          // Start Appium server in background
          sh '''
            nohup appium --log appium.log --relaxed-security --port 4723 > appium.out 2>&1 &
            echo $! > appium.pid
            sleep 8
          '''

          // Verify Appium is running
          def appiumStatus = sh(returnStatus: true, script: 'curl -s http://localhost:4723/status | grep -q "ready"')
          if (appiumStatus == 0) {
            echo "✅ Appium server started successfully"
          } else {
            echo "⚠️ Appium may not be ready, checking logs..."
            sh 'tail -50 appium.log || cat appium.out || echo "No logs found"'
          }
        }
      }
    }

    stage('Prechecks') {
      steps {
        script {
          def p = params.PLATFORM.toLowerCase()

          echo "✓ Vérification des prérequis pour la plateforme: ${p}"

          // Common checks - display warnings instead of failing
          sh '''
            echo "-- Checking basic tools --"
            if which adb >/dev/null 2>&1; then
              echo "✅ adb found: $(which adb)"
            else
              echo "⚠️ adb not found (needed for Android)"
            fi

            if which xcrun >/dev/null 2>&1; then
              echo "✅ xcrun found: $(which xcrun)"
            else
              echo "⚠️ xcrun not found (needed for iOS)"
            fi

            if which appium >/dev/null 2>&1; then
              echo "✅ appium found: $(which appium)"
              appium --version
            else
              echo "❌ appium not found"
              exit 1
            fi

            if which allure >/dev/null 2>&1; then
              echo "✅ allure found: $(which allure)"
            else
              echo "⚠️ allure not found (optional for report generation)"
            fi
          '''

          if (p == 'ios') {
            // Check iOS specific requirements
            def xcrunExists = sh(returnStatus: true, script: 'which xcrun >/dev/null 2>&1') == 0
            if (!xcrunExists) {
              error("❌ iOS prerequisite missing: 'xcrun' not found. Install Xcode command line tools.")
            }
            echo "✅ iOS prerequisites OK"

          } else if (p == 'android') {
            // Check Android specific requirements
            def adbExists = sh(returnStatus: true, script: 'which adb >/dev/null 2>&1') == 0
            if (!adbExists) {
              error("❌ Android prerequisite missing: 'adb' not found. Install Android SDK platform-tools.")
            }
            echo "✅ Android prerequisites OK"
          }
        }
      }
    }

    stage('Run Tests') {
      steps {
        script {
          def p = params.PLATFORM.toLowerCase()
          if (p == 'ios') {
            echo 'Running iOS tests'
            sh 'mvn -B -DplatformName=ios clean test'
          } else if (p == 'android') {
            echo 'Running Android tests'
            sh 'mvn -B -DplatformName=android clean test'
          } else {
            echo 'Running Web tests'
            sh 'mvn -B -DplatformName=web clean test'
          }
        }
      }
    }
  }

  post {
    always {
      script {
        // Stop Appium server if it was started
        echo "🛑 Stopping Appium server..."
        sh '''
          if [ -f appium.pid ]; then
            kill $(cat appium.pid) 2>/dev/null || true
            rm appium.pid
          fi
          pkill -f appium || true
        '''

        sh '''
          echo "📂 Listing target folder"
          ls -la target || true
        '''

        archiveArtifacts artifacts: 'target/**/*', allowEmptyArchive: true
        junit testResults: 'target/surefire-reports/**/*.xml', allowEmptyResults: true

        // Try to generate Allure report if CLI is present
        sh '''
          if [ -d target/allure-results ]; then
            if command -v allure >/dev/null 2>&1; then
              echo "📊 Generating Allure report..."
              allure generate target/allure-results --clean -o target/allure-report || true
            else
              echo "⚠️ Allure CLI not found, skipping generate"
            fi
          else
            echo "ℹ️ No allure-results found"
          fi
        '''

        echo "✅ Pipeline finished. Platform: ${params.PLATFORM}."
      }
    }

    cleanup {
      cleanWs()
    }
  }
}
