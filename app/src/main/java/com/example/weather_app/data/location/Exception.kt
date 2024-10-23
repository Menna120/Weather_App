package com.example.weather_app.data.location

import com.example.weather_app.data.location.ExceptionDescriptions.GPS_DISABLED_DESCR
import com.example.weather_app.data.location.ExceptionDescriptions.NO_INTERNET_CONNECTION_DESCR
import com.example.weather_app.data.location.ExceptionDescriptions.NO_PERMISSION_DESCR
import com.example.weather_app.data.location.ExceptionTitles.GPS_DISABLED
import com.example.weather_app.data.location.ExceptionTitles.NO_INTERNET_CONNECTION
import com.example.weather_app.data.location.ExceptionTitles.NO_PERMISSION


val EXCEPTIONS= mutableMapOf(
    GPS_DISABLED to GPS_DISABLED_DESCR,
    NO_INTERNET_CONNECTION to NO_INTERNET_CONNECTION_DESCR,
    NO_PERMISSION to NO_PERMISSION_DESCR
)

object ExceptionTitles {
    const val GPS_DISABLED = "GPS Disabled"
    const val NO_PERMISSION = "No Permission"
    const val NO_INTERNET_CONNECTION = "No Internet Connection"
}

object ExceptionDescriptions {
    const val GPS_DISABLED_DESCR = "Your GPS seems to be disabled, please enable it."
    const val NO_PERMISSION_DESCR = "Allow otherwise location tracking won't work."
    const val NO_INTERNET_CONNECTION_DESCR = "Please check your internet connection."
}