

# Tymema_v1 Project

## Overview

The Tymema_v1 project is a time management application designed to help users track their activities, manage goals, and improve productivity. This README provides an overview of the project structure, functionalities, and how to set it up.

## Table of Contents

1. [Introduction](#introduction)
2. [Features](#features)
3. [Project Structure](#project-structure)
4. [Setup Instructions](#setup-instructions)
5. [Contributing](#contributing)
6. [License](#license)

## Introduction

Tymema_v1 is an Android application developed using Kotlin programming language and Android SDK. It offers features such as creating time sheet entries, setting goals, managing categories, user authentication, and more. The project utilizes various Android components like Activities, Fragments, RecyclerView, SharedPreferences, and Intents.

## Features

### Main Features

- **Time Sheet Entries:** Users can create, view, and manage their time sheet entries, including date, start time, end time, description, and category.
- **Goal Tracking:** Users can set minimum and maximum goals for specific dates to track their productivity.
- **Category Management:** Users can create and manage categories for organizing their time sheet entries.
- **User Authentication:** Registration and login functionalities allow users to create accounts and securely access the application.

### Additional Features

- **Past Goal Records:** View past goal records to track progress over time.
- **Image Attachment:** Users can attach images to their time sheet entries.
- **Password Reset:** Forgot password functionality for user convenience.

## Project Structure

The project follows the MVC (Model-View-Controller) architecture pattern with some modifications to suit Android development:

- **Model:** Contains data classes (`TimeSheetEntries`) and shared preferences management for storing user data.
- **View:** Consists of various activities (`MainActivity`, `Login`, `Registration`, `Goals`, `Categories`, `CreateTimeSheetEntry`, `PastGoalRecords`, `EntryDetails`) and their respective layouts.
- **Controller:** Includes adapter classes (`TimeSheetAdapter`, `CategoryAdapter`) for RecyclerViews and interface (`RecyclerViewListener`) for handling click events.

## Setup Instructions

To run the Tymema_v1 project:

1. **Clone the Repository:** Clone this repository to your local machine using Git.
   ```bash
   git clone https://github.com/your_username/tymema_v1.git
   ```

2. **Open in Android Studio:** Open the project in Android Studio.

3. **Run the Application:** Build and run the application on an Android emulator or a physical device.

4. **Explore Features:** Explore the various features of the application, including time sheet entry creation, goal tracking, category management, and more.

## Contributing

Contributions to the Tymema_v1 project are welcome! If you'd like to contribute, please fork the repository, make your changes, and submit a pull request. For major changes, please open an issue first to discuss the proposed changes.

