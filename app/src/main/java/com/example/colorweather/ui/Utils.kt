package com.example.colorweather.ui

import android.text.format.DateFormat
import java.util.*

fun convertTime(timeStamp:Int, format: String):String{
    val cal = Calendar.getInstance(Locale.getDefault())
    cal.timeInMillis = (timeStamp * 1000L)
    val date = DateFormat.format(format,cal).toString().capitalize()
    return date

}