# Open Weather App

## Overview

The **Open Weather App** is an Android application that provides real-time weather information using live APIs. Built with Java and Android Studio, the app fetches weather data from the OpenWeather API and displays it to users in a clean and interactive interface. It uses `AsyncTask` for background data fetching, `JSONObject` for parsing JSON data, and buttons for user interactions.

## Features

- **Real-Time Weather Data:** Fetch and display current weather information using live data from the OpenWeather API.
- **Basic Weather Information:** Show key weather details including temperature, weather conditions, humidity, and wind speed.
- **Forecast Data:** Provide forecasts for upcoming days, if supported by the API endpoint used.
- **Asynchronous Data Fetching:** Utilize `AsyncTask` to handle background operations for data retrieval without blocking the UI thread.
- **Interactive User Interface:** Use buttons to allow users to initiate data fetching and interact with the app.
- **JSON Data Parsing:** Handle and parse weather data using `JSONObject` for structured data representation.

## Weather Features

The app provides a comprehensive set of weather-related features:

- **Current Temperature:** Display the current temperature for the selected location.
- **Weather Conditions:** Show detailed weather conditions, such as clear skies, rain, cloudy, etc.
- **Humidity Levels:** Provide information about the humidity percentage.
- **Wind Speed:** Display the speed of the wind in the current location.
- **Forecast Data:** If available, show weather forecasts for the next few days, including temperature and conditions.
