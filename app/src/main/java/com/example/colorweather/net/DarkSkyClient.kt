package com.example.colorweather.net

import com.example.colorweather.data.model.Weather
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object DarkSkyClient {

    private val darkSkyApi:DarkSkyApi

    private const val API_KEY = "1c989ff96909ee3edfc02eed676afabb"
    private const val DARK_SKY_URL ="https://api.darksky.net/"
    private val coordinates = Pair("37.8267","-122.4233")

    init{

        val retrofit = Retrofit.Builder()
            .baseUrl(DARK_SKY_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        darkSkyApi = retrofit.create(DarkSkyApi::class.java)

    }

    fun getWeather(latitude:String = coordinates.first, longitude:String = coordinates.second,lang:String = "es",units:String = "auto" ): Call<Weather> {

        return darkSkyApi.getWeather(API_KEY,latitude,longitude,lang,units)

    }

}