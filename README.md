# Final Android Project - Find Flights

Best Flights is Your go-to Android app for effortless flight search. Discover, compare and see details about your flights with ease.


## Table of Contents

- [About](#about)
- [Features](#features)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)

## About

Find Flights is a modern Android application designed to streamline the flight search and booking process. It offers users a user-friendly interface to search for flights, explore different destinations, and view detailed flight itineraries. The app provides a convenient and efficient way to find your next flight.

## Screenshots and Demo
<div style="display: flex; justify-content: space-between;">
<img src="/demo/demo.gif" width="200" height="400"/>
<img src="/demo/noflights.gif" width="200" height="400"/>
</div>
<div style="display: flex; justify-content: space-between;">

<img src="/demo/1.png" width="200" height="400"/>
<img src="/demo/2.png" width="200" height="400"/>
<img src="/demo/3.png" width="200" height="400"/>
<img src="/demo/4.png" width="200" height="400"/>
</div>
<div style="display: flex; justify-content: space-between;">
<img src="/demo/5.png" width="200" height="400"/>
<img src="/demo/6.png" width="200" height="400"/>
<img src="/demo/7.png" width="200" height="400"/>

</div>

## Features

Project Flights boasts several key features to enhance the user experience:

- **Flight Search:** The app allows users to search for flights by specifying the origin and destination airports, along with the desired travel date. The intuitive search interface simplifies the process of finding suitable flight options.

- **Airport Suggestions:** Users receive intelligent airport suggestions as they type in the origin and destination fields, making it easier to select the correct airports and reducing the chances of errors in flight selection.

- **Date Selection:** Project Flights includes a built-in date picker that enables users to select their travel date. The date picker is user-friendly and helps users choose their travel date accurately.

- **Real-time Flight Data:** The app provides real-time flight data, including flight itineraries, prices, and airline information. Users can view detailed information about available flights, allowing them to make informed decisions.

- **Error Handling:** Project Flights incorporates error handling to provide helpful feedback to users when issues arise, such as network problems or incomplete search queries. This ensures a smooth and frustration-free user experience.

- **User-Friendly Interface:** The app features a user-friendly and intuitive interface that caters to both novice and experienced travelers. It prioritizes simplicity and ease of use.

These features combine to create an Android application that simplifies the flight search process, helping users find the best flight options for their travel needs.

## Getting Started

To get started with Project Flights, follow these steps:

### Prerequisites

Before running the application, ensure you have the following prerequisites installed:

- **Android Studio:** Project Flights is an Android application, so you'll need Android Studio to build and run it. You can download Android Studio from [here](https://developer.android.com/studio).

### Installation

1. Clone the GitHub repository to your local machine using the following command:

   ```sh
   git clone https://github.com/rashadib/getFlights/tree/main

## Usage

Once the app is running, you can use Project Flights to search for and book flights with ease. Follow these steps to get started:

1. Launch the app on your Android device or emulator.

2. Enter the origin and destination airports in the respective search fields. The app will provide suggestions as you type, making it easy to select the correct airports.

3. Select a travel date using the date picker. You can choose your preferred departure date from the calendar.

4. Click the "Find Flights" button to initiate the flight search. Project Flights will retrieve a list of available flights based on your criteria.

5. Browse the list of available flights, each displaying essential information such as origin, destination, price, and airline. You can scroll through the list to explore all the options.

6. To view more details about a specific flight, tap on that flight's card. You'll see additional information, including layovers, departure times, and airline details.

7. If you find a flight that suits your preferences, you can proceed to book it through the app or the airline's website.

8. Enjoy your travel experience with Project Flights, your reliable flight search companion!

Project Flights simplifies the flight booking process, allowing you to find the best flight options quickly and efficiently. Whether you're planning a vacation or a business trip, this app has you covered.

Happy flying!

## Code Structure


- `app/` - Contains the main Android application code.
  - `adapter/` - Adapters for RecyclerViews.
  - `data/` - Data-related components.
  - `ui/` - User interface components.
- `data/` - Data services and API-related code.

## Architecture

* Fully written in Kotlin language.
* Built on MVVM architecture pattern.

## Libraries Used

- [Retrofit](https://square.github.io/retrofit/): For making network requests and handling API calls.
- [OkHttp](https://square.github.io/okhttp/): An HTTP client for efficient network communication.
- [Gson](https://github.com/google/gson): A library for JSON serialization and deserialization.
- [Picasso](https://square.github.io/picasso/): A powerful image loading library for Android.
- [ViewModel and LiveData](https://developer.android.com/topic/libraries/architecture/viewmodel): Android Architecture Components for UI data management.
- [RecyclerView](https://developer.android.com/guide/topics/ui/layout/recyclerview): A UI component for displaying large data sets.
- [ViewModelProvider](https://developer.android.com/reference/androidx/lifecycle/ViewModelProvider): For creating and managing ViewModel instances.
- [DatePickerDialog](https://developer.android.com/reference/android/app/DatePickerDialog): A dialog for selecting dates.
- [ViewModelScope](https://developer.android.com/kotlin/coroutines): Kotlin Coroutine library for launching coroutines within ViewModels.
- [HttpLoggingInterceptor](https://square.github.io/okhttp/4.x/okhttp/okhttp3/-http-logging-interceptor/): An OkHttp interceptor for logging HTTP requests and responses.
