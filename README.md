# Planiq 📱

A modern, feature-rich To-Do application built with Jetpack Compose for Android. Planiq helps you organize your tasks efficiently with an intuitive interface and powerful features.

## 📋 Table of Contents

- [Features](#features)
- [Screenshots](#screenshots)
- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [Project Structure](#project-structure)
- [Usage](#usage)
- [Key Components](#key-components)

## ✨ Features

### Core Functionality
- ✅ **Create Tasks**: Add new tasks with title, description, and priority
- ✅ **Update Tasks**: Edit existing tasks seamlessly
- ✅ **Delete Tasks**: Remove individual tasks or delete all at once
- ✅ **Undo Delete**: Restore accidentally deleted tasks with undo functionality
- ✅ **Search**: Search tasks by title or description
- ✅ **Sort by Priority**: Sort tasks by High, Medium, or Low priority
- ✅ **Persistent Sorting**: Your sorting preference is saved and restored

### User Experience
- 🎨 **Material Design 3**: Modern UI following Material Design 3 guidelines
- 🌙 **Dark Mode**: Automatic dark mode support based on system settings
- 🎭 **Splash Screen**: Animated splash screen with app logo
- 📱 **Swipe to Delete**: Intuitive swipe gestures for task deletion
- 🔔 **Snackbar Notifications**: User-friendly feedback for all actions
- ⚡ **Smooth Animations**: Fluid transitions and animations throughout the app

## 🛠 Tech Stack

### Core Technologies
- **Kotlin**: Primary programming language
- **Jetpack Compose**: Modern declarative UI toolkit
- **Material 3**: Latest Material Design components

### Architecture & Dependency Injection
- **MVVM (Model-View-ViewModel)**: Clean architecture pattern
- **Hilt**: Dependency injection framework
- **Repository Pattern**: Data abstraction layer

### Data Management
- **Room Database**: Local SQLite database for task persistence
- **DataStore Preferences**: For storing user preferences (sorting state)
- **Kotlin Coroutines & Flow**: Asynchronous programming and reactive data streams

### Navigation
- **Navigation Compose**: Type-safe navigation between screens

## 🏗 Architecture

The app follows the **MVVM (Model-View-ViewModel)** architecture pattern with a clean separation of concerns:

```
┌─────────────────┐
│   UI Layer      │  ← Jetpack Compose Screens
│  (Composables)  │
└────────┬────────┘
         │
┌────────▼────────┐
│  ViewModel      │  ← SharedViewModel (Business Logic)
│   (State)       │
└────────┬────────┘
         │
┌────────▼────────┐
│  Repository     │  ← Data Abstraction Layer
│   (Data)        │
└────────┬────────┘
         │
┌────────▼────────┐
│  Data Sources   │  ← Room DB, DataStore
│   (Persistence) │
└─────────────────┘
```

### Key Principles
- **Single Source of Truth**: Room Database as the primary data source
- **Reactive UI**: Flow-based state management for automatic UI updates
- **Dependency Injection**: Hilt for managing dependencies
- **Separation of Concerns**: Clear boundaries between UI, business logic, and data layers

## 📁 Project Structure

```
app/src/main/java/com/example/todoappjetpackcompose/
│
├── component/              # Reusable UI components
│   ├── DisplayAlertDialog.kt
│   ├── PriorityDropDown.kt
│   └── PriorityItem.kt
│
├── data/                   # Data layer
│   ├── dao/                # Room Database DAO
│   │   └── ToDoDao.kt
│   ├── models/             # Data models
│   │   ├── Priority.kt
│   │   └── ToDoTask.kt
│   ├── repositories/       # Repository implementations
│   │   ├── DataStoreRepository.kt
│   │   └── ToDoRepository.kt
│   └── ToDoDatabase.kt     # Room Database
│
├── di/                     # Dependency Injection
│   └── DatabaseModule.kt
│
├── navigation/             # Navigation setup
│   ├── destinations/       # Screen destinations
│   │   ├── ListComposable.kt
│   │   ├── SplashComposable.kt
│   │   └── Taskcomposable.kt
│   ├── Navigation.kt
│   └── Screens.kt
│
├── ui/                     # UI layer
│   ├── screens/            # Screen composables
│   │   ├── list/           # List screen components
│   │   ├── splash/         # Splash screen
│   │   └── task/           # Task screen components
│   ├── theme/              # App theming
│   │   ├── Color.kt
│   │   ├── Dimensions.kt
│   │   ├── Theme.kt
│   │   └── Type.kt
│   └── viewmodels/         # ViewModels
│       └── SharedViewModel.kt
│
├── util/                   # Utility classes
│   ├── Action.kt
│   ├── Constants.kt
│   ├── RequestState.kt
│   ├── SearchAppbarState.kt
│   └── TrailingIconState.kt
│
├── MainActivity.kt         # Main entry point
└── ToDoApplication.kt      # Application class
```

### Build Configuration

- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 34 (Android 14)
- **Compile SDK**: 34
- **Version Code**: 1
- **Version Name**: 1.0

## 📖 Usage

### Creating a Task
1. Tap the floating action button (FAB) on the list screen
2. Enter a title (max 20 characters) and description
3. Select a priority level (High, Medium, Low, or None)
4. Tap the save/update icon in the app bar

### Managing Tasks
- **Edit**: Tap on any task to edit it
- **Delete**: Swipe left on a task or use the delete icon
- **Undo**: After deleting, use the "UNDO" option in the snackbar
- **Search**: Tap the search icon and enter keywords
- **Sort**: Use the sort menu to organize by priority

### Priority Levels
- **High Priority** 🔴: Red indicator
- **Medium Priority** 🟡: Yellow indicator
- **Low Priority** 🟢: Green indicator
- **None**: Gray indicator

## 🔑 Key Components

### Screens

#### 1. Splash Screen
- Animated logo with fade-in and slide-up effects
- 3-second delay before navigating to the main screen
- Supports both light and dark themes

#### 2. List Screen
- Displays all tasks in a scrollable list
- Search functionality with real-time filtering
- Sort by priority (High to Low or Low to High)
- Swipe-to-delete gesture support
- Empty state when no tasks exist
- Floating action button for adding new tasks

#### 3. Task Screen
- Create or edit task details
- Title and description input fields
- Priority dropdown selector
- Form validation before saving
- Back navigation with action handling

## 👨‍💻 Development

### Code Style
- Follow Kotlin coding conventions
- Use meaningful variable and function names
- Add comments for complex logic
- Maintain clean architecture principles


---

**Planiq** - Plan your tasks, achieve your goals! 🚀

