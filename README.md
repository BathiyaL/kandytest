# KandyTest Automation Framework

> A flexible automation framework supporting Web UI and Mobile testing, with a configuration-driven architecture and unified reporting.  

[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](#license)  
[![Build Status](https://img.shields.io/badge/build-passing-brightgreen)](#)  

---

## 🧾 Table of Contents

- [About](#about)  
- [Features](#features)  
- [Supported Test Drivers](#supported-test-drivers)  
- [Getting Started](#getting-started)  
  - [Prerequisites](#prerequisites)  
  - [Installation](#installation)  
  - [Configuration](#configuration)  
  - [Running Tests](#running-tests)  
- [Project Structure](#project-structure)  
- [Writing Tests](#writing-tests)  
  - [Web UI Tests](#web-ui-tests)  
  - [Mobile Tests](#mobile-tests)  
- [Reporting & Test Results](#reporting--test-results)  
- [Contributing](#contributing)  
- [License](#license)  

---

## About

**KandyTest** (aka *kandy-test-automation-framework*) is a versatile automation framework intended to streamline UI and mobile automation testing using a unified architecture. It enables you to:

- Write tests in a driver-agnostic manner  
- Configure your test runs via annotations and profiles  
- Use page object patterns for cleaner test logic  
- Extend with custom test drivers (web, mobile)  
- Generate consolidated test reports  

⚙️ It’s especially suitable when you want one framework to cover multiple platforms (web and mobile) with minimal duplication.

---

## Features

- Annotation‑based test configuration for selecting test drivers (web, mobile)  
- Browser-based Web UI testing (e.g. Chrome)  
- Mobile (Android) testing support (using emulator or real devices)  
- Page object model for reusable UI interactions  
- Configuration of implicit waits, base URLs, device names, etc.  
- Centralized reporting and test lifecycle hooks  
- Separation of test logic and driver management  

---

## Supported Test Drivers

- **Web UI** — for automating browser interactions  
- **Mobile (Android)** — for automating mobile app testing (APK / emulator)  

You can select the driver via annotations on test classes or test methods (e.g. `@TestConfiguration(testDriver = TestDriver.WEB, browser = Browsers.CHROME, …)`).

---

## Getting Started

### Prerequisites

Make sure you have the following installed:

- Java 17+  
- Maven or your preferred build tool (if required)  
- Android SDK / emulator setup for mobile testing (if you run mobile tests)  
- Browser driver binaries (e.g. ChromeDriver) or WebDriver setup  
- The mobile APK file (for mobile test runs)  

### Installation

1. Clone this repository  
    ```bash
    git clone https://github.com/BathiyaL/kandytest.git
    cd kandytest
    ```  
2. Build / install dependencies (if applicable)  
    ```bash
    mvn clean install
    ```  
   or with your build tool of choice.

### Configuration

Configure test settings via annotations or configuration files. Common parameters include:

| Parameter | Purpose |
|----------|---------|
| `testDriver` | Specifies which driver to use (WEB or MOBILE) |
| `browser` | Browser type (e.g. `Chrome`) for web tests |
| `implicitlyWaitTime` | Timeout for implicit waits |
| `baseUrl` | Base URL for web tests |
| `mobileApp` | APK path for mobile testing |
| `mobileDeviceName` | Device name for emulator / real device |

Example:

```java
@TestConfiguration(
  testDriver = TestDriver.WEB,
  browser = Browsers.CHROME,
  implicitlyWaitTime = 15,
  baseUrl = "http://example.com"
)
public class MyWebTest {
   // test methods
}
```

### Running Tests

Run test classes via your build tool or IDE. For example:

```bash
mvn test -Dtest=MyWebTest
```

or using your preferred test runner configuration.

---

## Project Structure

```
kandytest/
│
├─ src/
│   ├─ main/ (if any helper or framework classes)
│   └─ test/
│       ├─ web/          # Web UI test classes
│       └─ mobile/       # Mobile test classes
│
├─ pom.xml or build file
├─ README.md
└─ .gitignore
```

- The **web/** folder holds test classes using Web driver  
- The **mobile/** folder holds mobile test classes  
- Shared utilities (page objects, driver factories, common helpers) may exist in a shared package  

---

## Writing Tests

### Web UI Tests

- Annotate a test class or method with `@TestConfiguration` for web driver  
- Use page object classes to represent pages and UI elements  
- Within test methods, call page object methods to interact with UI  
- Assertions should validate expected states  

**Example:**

```java
@TestConfiguration(
  testDriver = TestDriver.WEB,
  browser = Browsers.CHROME,
  implicitlyWaitTime = 15,
  baseUrl = "http://example.com"
)
public class MyWebUITest {
  @BeforeTest
  public void setup() {
    // open browser, navigate, etc.
  }

  @Test
  public void verifyHomePageTitle() {
    HomePage home = getWebPage(HomePage.class);
    String title = home.getTitle();
    assertThat(title).isEqualTo("Expected Title");
  }
}
```

### Mobile Tests

- Annotate test class or method with `@TestConfiguration` for mobile driver  
- Provide `mobileApp` (APK) and `mobileDeviceName`  
- Use mobile page object classes to interact with UI elements  

**Example:**

```java
@TestConfiguration(
  testDriver = TestDriver.MOBILE,
  mobileApp = "Demo.apk",
  mobileDeviceName = "Pixel_3_API_30"
)
public class MyAndroidTest {
  @BeforeTest
  public void setup() {
    // launch app, prepare context
  }

  @Test
  public void testLogin() {
    AndroidLoginPage login = getAndroidPage(AndroidLoginPage.class);
    login.enterCredentials("user", "pass");
    login.tapLogin();
    assertTrue(login.isHomeScreenDisplayed());
  }
}
```

---

## Reporting & Test Results

- After test execution, detailed test reports (logs, screenshots, status) will be generated  
- For failures, capture screenshots or logs to help debugging  
- Consolidate test results for both web and mobile runs  

You may want to integrate a report generator (e.g. Allure, ExtentReports) or CI pipeline to fetch test artifacts and display trends.
<img width="1505" alt="Screenshot 2024-03-07 at 00 32 29" src="https://github.com/BathiyaL/kandytest/assets/15939220/92321760-b1f9-48e9-9a95-0a14dffb41a9">

---

## Contributing

Contributions are always welcome! Here’s how you can help:

1. Fork the repository  
2. Create a new feature branch (`git checkout -b feat/new-test-driver`)  
3. Make your changes  
4. Ensure all existing tests pass  
5. Add new tests if applicable  
6. Open a Pull Request and describe your changes  

Please follow existing code style and include tests and documentation.

---

## License

This project is licensed under the **MIT License** (or your preferred license).  
See the [LICENSE](LICENSE) file for details.

---

Thank you for using **KandyTest**!
