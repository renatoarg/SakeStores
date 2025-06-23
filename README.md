# 🍶 SakeStores - Android Application

A modern Android application for discovering local sake shops, built with **Jetpack Compose** and **Clean Architecture** principles.

---

## 📋 Project Overview

This application provides users with:
- **Browse** local sake shops in an intuitive list view
- **Explore** detailed information about each sake shop
- **Navigate** to shop locations via Maps integration
- **Visit** shop websites through integrated web browser

---

## ⚙️ Technical Configuration

### Development Environment
| Component | Version | Details |
|-----------|---------|---------|
| **Android Studio** | Meerkat 2024.3.1 Patch 2 | Latest stable release |
| **Gradle JDK** | OpenJDK 17.0.5 | Homebrew distribution (ARM64) |
| **Java** | 17 | Long-term support version |
| **Android API** | 35 (Android 15) | Target SDK version |
| **SDK Tools** | 35.0.0 | Latest SDK tools |
| **Gradle Wrapper** | 8.11.1 | Build automation |
| **Android Gradle Plugin** | 8.2.0 | Build system integration |

### Architecture Stack
- **🏗️ Clean Architecture** - Separation of concerns with distinct layers
- **📦 Multi-Module Design** - Feature-based modularization
- **🎨 Jetpack Compose** - Modern declarative UI framework
- **🧭 Navigation Compose** - Type-safe navigation
- **💉 Dependency Injection** - Modular and testable code structure

---

## 🧪 Testing & Quality Assurance

### Test Coverage
The project implements comprehensive testing strategies:

- ✅ **Unit Tests** - Business logic validation
- ✅ **Instrumented Tests** - UI behavior verification
- ✅ **Integration Tests** - Component interaction testing

### Running Tests

#### Generate Complete Test Report
```bash
./gradlew jacocoFullReport
```

#### View Coverage Report
```bash
open build/reports/jacoco/jacocoFullReport/html/index.html
```

### Test Metrics
- **Test Types**: Unit, Integration, UI/Snapshot tests
- **Reporting**: Jacoco integration for detailed metrics

---

## 📚 Documentation

### Code Documentation
- **📝 KDoc** - Comprehensive inline documentation for all classes

### Generate Documentation
```bash
./gradlew copyDokkaDocsToRoot
```

### View Documentation
```bash
open docs/index.html
```

---

## ✅ Requirements Compliance

### Core Features
| Requirement | Status | Implementation |
|-------------|--------|----------------|
| **List Screen** | ✅ **DONE** | Displays sake shops with names, addresses, ratings |
| **Details Screen** | ✅ **DONE** | Complete shop information with interactive elements |
| **JSON Data Integration** | ✅ **DONE** | Structured data parsing and display |
| **Navigation** | ✅ **DONE** | Seamless list-to-details transition |
| **Maps Integration** | ✅ **DONE** | Clickable addresses open in Maps app |
| **Web Integration** | ✅ **DONE** | Shop websites open in browser/custom tabs |

### Technical Excellence
| Aspect | Status | Details |
|--------|--------|---------|
| **Clean Architecture** | ✅ **DONE** | Presentation, Domain, Data layers |
| **Modular Design** | ✅ **DONE** | Feature-based module separation |
| **Jetpack Compose** | ✅ **DONE** | 100% Compose UI implementation |
| **Code Quality** | ✅ **DONE** | Well-documented, readable, maintainable |
| **User Experience** | ✅ **DONE** | Material Design 3 guidelines |
| **Testing Coverage** | ✅ **DONE** | Unit, UI, and Snapshot tests |
| **Reusability** | ✅ **DONE** | Modular architecture enables easy extension |

---

## 🏗️ Architecture Overview

### Layer Separation
```
┌──────────────────────────────────────┐
│           PRESENTATION               │
│  ┌─────────────────────────────────┐ │
│  │     feat-sake-list              │ │
│  │     feat-sake-details           │ │
│  │     system-design               │ │
│  └─────────────────────────────────┘ │
└──────────────────────────────────────┘
┌──────────────────────────────────────┐
│             DOMAIN                   │
│  ┌─────────────────────────────────┐ │
│  │   Models • Use Cases • Repos    │ │
│  └─────────────────────────────────┘ │
└──────────────────────────────────────┘
┌──────────────────────────────────────┐
│              DATA                    │
│  ┌─────────────────────────────────┐ │
│  │   Repositories • Data Sources   │ │
│  └─────────────────────────────────┘ │
└──────────────────────────────────────┘
```

### Module Structure
- **`app/`** - Application entry point and dependency injection
- **`domain/`** - Business logic and data models
- **`data/`** - Data access layer and repository implementations
- **`feat-sake-list/`** - Sake shop list feature module
- **`feat-sake-details/`** - Sake shop details feature module
- **`system-design/`** - Sake shop theme, colors, types and more

---

## 🎨 User Experience

### Design Principles
- **Material Design 3** - Modern, accessible interface
- **Intuitive Navigation** - Clear user journey
- **Performance Optimized** - Smooth interactions and transitions
- **Responsive Design** - Adapts to different screen sizes

### Key Interactions
1. **Browse** sake shops in a visually appealing list
2. **Tap** any shop to view comprehensive details
3. **Navigate** to shop location with one tap
4. **Visit** shop website seamlessly
5. **Return** to list with intuitive back navigation

---

## 🚀 Getting Started

### Prerequisites
- **Android Studio** Meerkat 2024.3.1+
- **JDK 17**
- **Android SDK** with API 35

### Setup Instructions

1. **Unpack the project**
   ```bash
   cd SakeStores
   ```

2. **Open in Android Studio**
 - Launch Android Studio
 - Select "Open an Existing Project"
 - Navigate to the unpacked directory

3. **Build the project**
   ```bash
   ./gradlew build
   ```

4. **Run the application**
 - Connect an Android device or start an emulator
 - Click the "Run" button or use `Ctrl+R`

---

## 🔄 Future Enhancements

### Planned Features
- **🔍 Search & Filter** - Find shops by name, rating, or location
- **🌙 Dark Mode** - Enhanced accessibility and user preference
- **🌐 Internationalization** - Multi-language support
- **🌐 Connect real API** - Web based list of sake shops

### Technical Improvements
- **⚡ Performance** - Image caching
---

## 📦 Dependencies

### Core Libraries
```kotlin
// UI Framework
androidx.compose.ui
androidx.compose.material3

// Navigation
androidx.navigation.compose

// Architecture
androidx.lifecycle.viewmodel-compose
androidx.hilt.navigation.compose

// Image Loading
io.coil-kt:coil-compose

// Testing
junit
androidx.test.ext:junit
androidx.compose.ui:ui-test-junit4
```

Requirements:

- Solution works: DONE
- Code is clean, well-written, easy to read and contains documentation: DONE
- Functionally works and satisfies the requirements below: DONE
- Clear architecture / Modular architecture is implemented: DONE
- Thoughtful design and user experience is a plus: DONE
- Code is unit tested and with UI/SnapshotTests. Other kinds of tests will be considered pluses: DONE
- We incentivize you to perform mindful choices in your software solution and document them in a README, so the evaluators can
  follow your train of thought. If you cannot meet all the requirements (due to time constraints or any other reasons), be sure to
  inform that as well. If you provide a list of things you would pick to implement next if you were to maintain this project for a long-
  time, that will be taken as a plus: DONE


For this assignment, you will build a simple app that allows the user to see
1 - A screen with a list of local sake shops and: DONE
2 - A details screen for a specific sake shop: DONE

Requirements:
- The data is going to be supplied in JSON format: DONE
- The list page should contain items with sake shop names, address, and star ratings: DONE
- Whenever a sake shop from the list is tapped, the user should be directed to the details page of that shop.: DONE

The details page should contain details about the sake shops, including:
- Shop name.: DONE
- Shop picture.: DONE
- A brief description.: DONE
- A rating in stars.: DONE
- Address (clickable, should open address in a Maps app or browser).: DONE
- A button or link to visit the shop's website in default web browser or in a custom tab.: DONE

Instructions:
. Build a list page component that displays all items from the supplied JSON data you got together with this assignment.
. You will be evaluated based on at least some of the following:

a. Modular Architecture Approach: DONE - Clean Architecture with Presentation Layer modularized in List, Details and System Design
b. Clean Architecture: DONE - Presentation (modularized), Domain and Data Layers implemented
c. Jetpack compose must be used as the native development framework for this assignment: DONE
d. Re-usability of your solution (could someone re-implement your solution easily for a different type of data / different API? ).
e. Good UX and UI will be considered pluses, but you don't need to spend a very long time pixel-perfecting the look and feel
(we don't expect you to be a designer). Follow material design guidelines and you're good.: DONE
f. Other factors include: any tests included, ui / snapshot tests, component structure, standards, performance and stability,
etc.: DONE
. Feel free to show off your coding ability by adding any level of appropriate complexity.: DONE
. Include a README that details information about your project, dependencies, setup, and requirements.: DONE
i. XML views submission will not be accepted.: DONE

---