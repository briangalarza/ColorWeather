package com.example.colorweather.data.model

import com.example.colorweather.data.model.*

data class Weather(
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val currently: Currently,
    val minutely: Minutely,
    val hourly: Hourly,
    val daily: Daily,
    val alerts: List<Alerts>,
    val flags: Flags,
    val offset: Int
)