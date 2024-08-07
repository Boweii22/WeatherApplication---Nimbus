![505shots_so](https://github.com/user-attachments/assets/84516a55-fb0f-40b9-af4f-cc59efb3fce6)

# Nimbus Weather App

Nimbus is an Android application that provides real-time weather information for any city. The app uses the OpenWeatherMap API to fetch and display weather data such as temperature, humidity, pressure, wind speed, and more. The app also adjusts its background video based on the time of day.

## Features

- Fetches and displays weather information for a specified city. 
- Displays temperature, humidity, pressure, wind speed, sunrise, and sunset times.
- Changes background video based on the time of day (day or night). /**Feel Free to work on this part**/
- Search functionality to look up weather information for different cities.
- Fullscreen mode for an immersive user experience.

## Prerequisites

- Android Studio 4.0 or higher
- Android SDK
- A valid OpenWeatherMap API key

## Getting Started

Follow these instructions to set up and run the Nimbus Weather App on your local machine.

### Installation

1. **Clone the repository:**

    ```bash
    git clone https://github.com/yourusername/nimbus-weather-app.git
    cd nimbus-weather-app
    ```

2. **Open the project in Android Studio:**
    - Launch Android Studio.
    - Select `Open an existing project`.
    - Navigate to the cloned repository directory and select it.

3. **Add your OpenWeatherMap API key:**
    - Open `MainActivity.kt`.
    - Replace the `API` value with your OpenWeatherMap API key:

      ```kotlin
      private val API: String = "YOUR_API_KEY_HERE"
      ```

4. **Build and run the app:**
    - Connect your Android device or start an emulator.
    - Click on the `Run` button in Android Studio.

## Usage

1. **Search for a city's weather:**
    - Launch the Nimbus app.
    - Enter a city name in the search bar at the top.
    - Press the search icon on the keyboard.

2. **View weather information:**
    - The app will fetch and display the current weather information for the specified city.

3. **Background video:**
    - The background video changes based on the time of day (daytime or nighttime).

## Code Overview

### MainActivity.kt

- **Initialization:** Sets the app to fullscreen mode and initializes the `SearchView`.
- **Search Functionality:** Listens for search queries and fetches weather data for the specified city.
- **Weather Data Fetching:** Uses `AsyncTask` to fetch weather data from the OpenWeatherMap API in the background.
- **UI Updates:** Updates the UI with fetched weather data, including temperature, humidity, pressure, wind speed, and more.
- **Background Video:** Changes the background video based on the current time (day or night).

### Layouts

- **activity_main.xml:** Defines the UI layout of the main activity, including `SearchView`, `TextView`s for weather information, and `ProgressBar` for loading state.


## Acknowledgements

- [OpenWeatherMap](https://openweathermap.org/) for the weather data API.
- Android developers and the community for resources and support.

---

Happy coding! 🌦️
