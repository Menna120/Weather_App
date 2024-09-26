package com.example.weather_app.domain.util

sealed class RemoteResponse<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?): RemoteResponse<T>(data)
    class Error<T>(message: String, data: T? = null): RemoteResponse<T>(data, message)
}