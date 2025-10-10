# Multimodule Clean Architecture Jetpack Compose Boilerplate

## Overview

A modular Android boilerplate project built with Kotlin, demonstrating **Clean Architecture** principles and **Jetpack
Compose** for UI. The project uses Gradle for build management, Hilt for dependency injection, Mixpanel for analytics, and
implements the **MVI (Model-View-Intent)** pattern for robust state management.

## Goals
- Provide a scalable boilerplate for modular Android apps
- Enable easy feature addition and maintenance
- Promote separation of concerns and testability

## Features
- Multi-module Gradle setup
- Jetpack Compose for declarative UI
- Clean Architecture (data, domain, presentation layers)
- MVI pattern for predictable state management
- Hilt for dependency injection
- Mixpanel analytics integration

## Architecture

### Clean Architecture Layers
- **Presentation**: UI components and ViewModels, handling user interaction and state.
- **Domain**: Business logic, use cases, and entities, independent of frameworks.
- **Data**: Repositories, data sources, networking, and persistence.

### MVI Pattern
The app uses the Model-View-Intent (MVI) pattern in the presentation layer:
- **Model**: Represents the UI state.
- **View**: Displays the state and forwards user events.
- **Intent**: User actions or events that trigger state changes.
  This ensures a unidirectional data flow, making UI logic predictable and easier to test.

## Modules

* `app`: Main application entry point, sets up dependency injection and navigation.
* `feature:random`: Feature module for random-related functionality, implemented with MVI.
* `feature:explore`: Feature module for explore-related functionality, implemented with MVI.
* `feature:favorites`: Feature module for managing favorites, with its own API submodule.
* `feature:similar`: Feature module for similar content, implemented with MVI.
* `core:data`: Shared data logic and mappers.
* `core:domain`: Shared domain models.
* `core:network`: Networking logic, API communication, and remote data sources.
* `core:datastore`: Data persistence and storage utilities.
* `core:analytics`: Analytics and event tracking integration, including Mixpanel.
* `core:navigation`: Centralized navigation logic.
* `core:ui`: Shared UI components and utilities.

## Analytics

- **Mixpanel**: Used for tracking user events and app usage. Integrated via the `core:analytics` module for centralized event management.

## Setup

1. Clone the repository:
   ```sh
   git clone git@github.com:aregpetrosyan/Multimodule-Clean-Architecture-Jetpack-Compose.git