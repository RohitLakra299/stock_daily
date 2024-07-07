# Stock Tracker App

A Kotlin-based Android application for tracking stock market data, including top gainers, top losers, and detailed stock information.

## Features

- **Top Gainers and Top Losers:** View the top gaining and losing stocks directly on the home screen.
- **Stock Ticker Search:** Search for stock tickers to get detailed information.
- **Categorized Search:** Implemented categorized chips for efficient stock ticker searching.
- **Light and Dark Mode:** Supports both light and dark themes for user preference.
- **Line Graph:** Displays a line graph showing daily closing prices of selected stocks.
- **Data Caching:** Utilizes Room database for caching stock data to improve app performance.
- **API Integration:** Uses Retrofit for making API calls to AlphaVantage for real-time stock data.
<br/>
<a href="https://www.youtube.com/watch?v=u9P1g6CTznQ">Video Link</a>
<br/>
<a href="https://drive.google.com/file/d/1sFp2wehE9Yrwe-aPGkqtXTttgCrgoXKn/view?usp=sharing">Apk Link</a>
<br/>

## Screenshots

<div style="display:flex;flex-wrap:wrap;">
  <img src ="https://github.com/RohitLakra299/stock_daily/assets/78154259/5b2c7670-2eef-4713-870a-bad534817dd4" width="300px" height="620px"/>
  <img src ="https://github.com/RohitLakra299/stock_daily/assets/78154259/b0bc0cd5-d384-4fca-910f-ec100343a8b5" width="300px" height="620px"/>
  <img src ="https://github.com/RohitLakra299/stock_daily/assets/78154259/bb4bd34b-d15f-4995-95f5-293e90a97f87" width="300px" height="620px"/>
  <img src ="https://github.com/RohitLakra299/stock_daily/assets/78154259/abdc08f5-c472-42a9-891e-be050b7a9bc4" width="300px" height="620px"/>
    <img src ="https://github.com/RohitLakra299/stock_daily/assets/78154259/c41f56aa-094c-422a-9b89-ba1e3c03140d" width="300px" height="620px"/>
<img src ="https://github.com/RohitLakra299/stock_daily/assets/78154259/b99d1751-dac8-4840-befd-48f95c76a893" width="300px" height="620px"/>
</div>

## Installation

1. Clone the repository.
2. Open the project in Android Studio.
3. Build and run the project on an Android device or emulator.

## Dependencies

- Kotlin
- Android Jetpack Components (ViewModel, LiveData, Room)
- Retrofit for API calls
- MPAndroidChart for displaying line graphs

### Note
-  Limit of API is 25 request/day. Please use accordingly.
-  In case old api runs out of requests, try using this api `7M3WCYQD7IR3M7Y3`
