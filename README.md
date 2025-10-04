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

- Annotation-based test configuration for selecting test drivers (web, mobile)  
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


Reporting & Test Results

After test execution, detailed test reports (logs, screenshots, status) will be generated

For failures, capture screenshots or logs to help debugging

Consolidate test results for both web and mobile runs

You may want to integrate a report generator (e.g. Allure, ExtentReports) or CI pipeline to fetch test artifacts and display trends.
<img width="1505" alt="Screenshot 2024-03-07 at 00 32 29" src="https://github.com/BathiyaL/kandytest/assets/15939220/92321760-b1f9-48e9-9a95-0a14dffb41a9">

Contributing

Contributions are always welcome! Here’s how you can help:

Fork the repository

Create a new feature branch (git checkout -b feat/new-test-driver)

Make your changes

Ensure all existing tests pass

Add new tests if applicable

Open a Pull Request and describe your changes

Please follow existing code style and include tests and documentation.

