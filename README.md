<div align="center">

# 💎 WIGL 💰
*Finance & Crypto Made Simple*

# 🌟 Tests E2E Web & Mobile Wigl

[![Tests](https://img.shields.io/badge/Tests-Passing-success?style=for-the-badge&logo=github)](https://github.com/hakantetik44/WiglMobile_E2E)
[![Selenium](https://img.shields.io/badge/Selenium-4.0-green?style=for-the-badge&logo=selenium)](https://www.selenium.dev)
[![Appium](https://img.shields.io/badge/Appium-2.0-purple?style=for-the-badge&logo=appium)](https://appium.io)
[![Cucumber](https://img.shields.io/badge/Cucumber-BDD-brightgreen?style=for-the-badge&logo=cucumber)](https://cucumber.io)
[![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)](https://www.java.com)
[![Maven](https://img.shields.io/badge/Maven-3.8-red?style=for-the-badge&logo=apache-maven)](https://maven.apache.org)

*Framework for automated testing of Wigl web and mobile applications*

[📱 About](#-about) •
[🚀 Installation](#-installation) •
[📊 Reports](#-reports) •
[📞 Contact](#-contact)

---

</div>

## 💫 About
End-to-end testing framework for the Wigl application. This automated test suite validates the proper functioning of:
- 🌐 The responsive web application
- 📱 The Android and iOS mobile applications
- 🔄 Data synchronization between platforms
- 🎮 Gaming and rewards features
- 💰 Cashback and loyalty points system

## ⚡ Technologies Used
- 🌐 **Selenium**: Automated web testing
- 📱 **Appium**: Automated mobile testing
- 🥒 **Cucumber**: BDD specifications
- ☕ **Java**: Programming language
- 🎯 **Maven**: Dependency management
- 🧪 **JUnit**: Testing framework
- 📊 **Allure**: Test reporting

## 📋 Prerequisites

### 🌐 Web Tests
- ☕ Java JDK 17
- 🎯 Maven 3.8.x+
- 🌐 Browsers:
  - Chrome
  - Firefox
  - Safari
  - Edge

### 📱 Mobile Tests
- 💻 Node.js and npm
- 📱 Appium 2.0+
- 🤖 Android Studio & SDK
- 🍎 Xcode (for iOS)

## 🚀 Installation

### 1. 📥 Clone the repository
```bash
git clone https://github.com/hakantetik44/WiglMobile_E2E.git
cd WiglMobile_E2E
```

### 2. 📦 Install dependencies
```bash
mvn clean install
```

### 3. ⚙️ Configuration

#### 🌐 Web
Edit `src/test/resources/configuration.properties` to set web testing parameters.

#### 📱 Mobile
Edit `src/test/resources/configuration.properties` to set mobile testing parameters:

##### Android Configuration
```properties
android.platform.name=Android
android.platform.version=11
android.device.name=sdk_gphone_x86
android.udid=emulator-5554
android.app.package=com.bps.wigl
android.app.activity=com.bps.wigl.MainActivity
android.no.reset=true
android.auto.grant.permissions=true
android.automation.name=UiAutomator2
```

##### iOS Configuration
```properties
ios.platform.name=iOS
ios.platform.version=18.3.1
ios.device.name=iPhone
ios.udid=your-device-udid
ios.bundle.id=com.bps.wigl
ios.automation.name=XCUITest
```

## ▶️ Running Tests

### 🎯 All tests
```bash
mvn clean test -Dcucumber.filter.tags="@all"
```

### 🌐 Web Tests
```bash
mvn test -Dplatform=web -Dcucumber.filter.tags="@web"
```

### 📱 Mobile Tests
```bash
# Android
mvn test -DplatformName=android -Dcucumber.filter.tags="@android"

# iOS
mvn test -DplatformName=ios -Dcucumber.filter.tags="@ios"
```

### 🏷️ Tests by Module
```bash
# Login tests
mvn test -Dcucumber.filter.tags="@login"

# Registration tests
mvn test -Dcucumber.filter.tags="@registration"

# Payment tests
mvn test -Dcucumber.filter.tags="@payment"
```

## 📱 iOS Testing Specifics

### WebDriverAgent Setup
For iOS testing, WebDriverAgent needs to be properly set up:

1. Install WebDriverAgent:
   ```bash
   npm install -g appium
   appium driver install xcuitest
   ```

2. Open WebDriverAgent in Xcode:
   ```bash
   open -a Xcode ~/.appium/node_modules/appium-xcuitest-driver/node_modules/appium-webdriveragent/WebDriverAgent.xcodeproj
   ```

3. Configure WebDriverAgent:
   - Select the WebDriverAgentRunner scheme
   - Choose your iOS device as the target
   - Update the signing team to your Apple Developer account
   - Build the project (⌘+B)

4. Trust the developer certificate on your iOS device:
   - Go to Settings > General > Device Management
   - Select your Apple Developer account
   - Tap "Trust"

5. Run WebDriverAgentRunner directly on your device:
   - Launch the WebDriverAgentRunner app on your device
   - Ensure it's running before starting Appium tests

## 🤖 Android Testing Specifics

1. Set up Android SDK:
   ```bash
   sdkmanager "platform-tools" "platforms;android-30" "build-tools;30.0.3"
   ```

2. Create and start an emulator:
   ```bash
   avdmanager create avd -n test_device -k "system-images;android-30;google_apis;x86_64"
   emulator -avd test_device
   ```

3. Install the Wigl app:
   ```bash
   adb install -r path/to/wigl.apk
   ```

4. Grant necessary permissions:
   ```bash
   adb shell pm grant com.bps.wigl android.permission.ACCESS_FINE_LOCATION
   ```

## 📊 Reports and Analysis

### 📈 Allure Reports
Allure reports are automatically generated in `target/allure-results` and include:
- Test overview
- Error screenshots
- Detailed execution time
- Quality metrics
- Execution history

To view the reports:
```bash
allure serve target/allure-results
```

### 📑 Cucumber Reports
Cucumber reports are available in `target/cucumber-reports`:
- Interactive HTML reports
- JSON reports for CI/CD integration
- XML reports for trend analysis

To open the HTML report:
```bash
open target/cucumber-reports/index.html
```

## 🔄 Continuous Integration (CI/CD)

The project can be integrated with CI/CD systems like Jenkins for automated testing.

## 🤝 Contribution
1. 🔀 Fork the project
2. 🌿 Create a branch (`git checkout -b feature/AmazingFeature`)
3. ✍️ Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. 📤 Push to the branch (`git push origin feature/AmazingFeature`)
5. 🔍 Open a Pull Request

## 📞 Contact
- 🌐 **Website**: [www.wigl.fr](https://wigl.fr)
- 📧 **Email**: contact@wigl.fr

<div align="center">

---

# 💎 WIGL 💰
*Finance & Crypto Made Simple*

*Developed with ❤️ by the Wigl QA Team*

</div>
