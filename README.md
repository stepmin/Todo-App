# Kotlin Multiplatform ToDo App (KMP ToDo)

A sample ToDo application built with Compose Multiplatform

## Project Overview

This project demonstrates how to build a cross-platform ToDo application using Kotlin Multiplatform (KMP). It leverages Jetpack Compose for the UI layer and Koin for dependency injection. The application is designed with a clean architecture, separating concerns and promoting code reusability.

**Key Technologies**

*   **Presentation Layer (`presentation`):**
  *   **ViewModels:** Responsible for managing UI-related data and state. They interact with the domain layer and expose data to the UI using `StateFlow`.
  *   **Jetpack Compose:** UI built with the modern declarative UI toolkit.
  *   **Navigation:** To navigate between screens.
*   **Domain Layer (`domain`):**
  *   **Use Cases:** Represent specific business operations. They encapsulate the application's logic and interact with the data layer.
  *   **Entities:** Data models that represent the core concepts of the application (e.g., `Task`).
*   **Data Layer (`data`):**
  *   **Data Source:** Responsible to get the data.
*   **Infrastructure Layer (`infrastructure`)**:
  *   Provides platform-independent interfaces and implementations for common tasks like networking, database access, file handling, etc. This layer isolates the core application logic from external dependencies, making it easier to test and maintain.
*   **Dependency Injection (`di`):**
  *   **Koin:** Used to inject dependencies into classes, making them more testable and modular.
*   **Base:**
  *   **`BaseViewModel`:** Base class for the view models.
  *   **`VmState`:** Base class for the states.
  *   **`VmIntent`:** Base class for the intents.
  *   **`VmEvent`:** Base class for the events.

## Features

*   **Task Management:**
  *   Mark tasks as completed.
  *   Edit task details.
*   **Clean Architecture:** The code is organized into clear layers, promoting maintainability and testability.
*   **Dependency Injection:** Koin is used to inject dependencies, making the code more modular and flexible.
* **ViewModel**: Each screen has a `ViewModel`.
* **StateFlow**: To update the ui.

## Project Structure

The project follows a multi-module structure, which is a best practice for KMM projects. Here's a breakdown:

*   **`androidApp/`:** The Android application module, containing Android-specific code, resources, and the UI built with Jetpack Compose.
*   **`shared/`:** The Kotlin Multiplatform Mobile (KMM) module, containing the shared code that can be used by both Android and iOS (if you add it).
  *   **`commonMain/`:** This is where most of your business logic, domain models, use cases, and data layer will reside.
  *   **`androidMain/`:** Any Android-specific code that the shared module needs can go here.
  *   **`iosMain/`:** Any iOS-specific code would go here (if you add iOS).
* **`base/`**: Contains the base classes.
* **`data/`**: Contains the data source.
* **`di/`**: Contains the dependency injection code.
* **`domain/`**: Contains the logic of the project.
* **`presentation/`**: Contains the view model, the navigation and the ui.