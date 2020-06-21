package com.example.colorweather.net

import com.example.colorweather.data.model.Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface DarkSkyApi {
    @GET ("forecast/{api_key}/{latitude},{longitude}")
    fun getWeather(
        @Path("api_key")api_key:String,
        @Path("latitude")latitude:String,
        @Path("longitude")longitude:String,
        @Query("lang")lang:String,
        @Query("units")units:String
        ): Call<Weather>


}