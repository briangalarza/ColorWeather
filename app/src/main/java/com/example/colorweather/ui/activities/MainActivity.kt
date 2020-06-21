package com.example.colorweather.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import com.example.colorweather.data.model.Currently
import com.example.colorweather.data.model.Weather
import com.example.colorweather.net.DarkSkyClient
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.design.indefiniteSnackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt
import android.text.format.DateFormat
import com.example.colorweather.R
import com.example.colorweather.data.model.Data
import com.example.colorweather.ui.convertTime

class MainActivity : AppCompatActivity() {

    companion object{
        const val HOURLY_SUMMARY = "HOURLY_SUMMARY"
        const val DAILY_DATA = "DAILY_DATA"
    }

    var hourlySummary: List<String>? = null
    var dailyData:List<Data>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getWeather()
    }


    private fun getWeather(){
        //Desplegar nuestra progress bar
        //Nuestros componenetes sean invisibles
        displayProgressBar(true)
        displayUI(false)

        val lang = getString(R.string.lang)


        DarkSkyClient.getWeather(lang = lang).enqueue(object : Callback<Weather>{

            override fun onFailure(call: Call<Weather>, t: Throwable) {
               displayProgressBar(false)
                displayUI(false)
                //En caso de error
                //LLamar a una función que despliegue mensaje de error
                displayErrorMessage()
            }

            override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                //ocultar nuestra progress bar
                displayProgressBar(false)
                //Desplegar nuestra UI
                displayUI(true)
                if (response.isSuccessful){

                    hourlySummary = response.body()?.hourly?.data?.map { "${convertTime(it.time,"MMMM dd, hh:mm ")}${it.summary}" }
                    dailyData = response.body()?.daily?.data
                    setUpWidgets(response.body()?.currently)
                }else{
                    displayErrorMessage()
                }


            }

        })
    }

    private fun displayErrorMessage(){
       mainLayout.indefiniteSnackbar(getString(R.string.network_error),getString(
           R.string.Ok
       )){
           getWeather()
       }
    }

    private fun displayProgressBar(visible: Boolean){
        progressBar.visibility = if(visible) View.VISIBLE else View.GONE
    }

    private fun displayUI(visible:Boolean){
        dateTextView.visibility = if (visible) View.VISIBLE else View.GONE
        iconImageView.visibility = if (visible) View.VISIBLE else View.GONE
        descriptionTextView.visibility = if (visible) View.VISIBLE else View.GONE
        minTempTextView.visibility = if (visible) View.VISIBLE else View.GONE
        precipProbTextView.visibility = if (visible) View.VISIBLE else View.GONE
        hourlyButton.visibility = if (visible) View.VISIBLE else View.GONE
        dailyButton.visibility = if (visible) View.VISIBLE else View.GONE


    }

    private fun setUpWidgets(currently: Currently?){
        descriptionTextView.text = currently?.summary ?: getString(R.string.no_data)
        minTempTextView.text = getString(R.string.temp,currently?.temperature?.roundToInt().toString())
        precipProbTextView.text = getString(R.string.precip_prob,currently?.precipProbability?.roundToInt())
        iconImageView.setImageResource(getWeatherIcon(currently?.icon ?: "clear_day"))
        dateTextView.text = getDateTime()?.capitalize()?: getString(R.string.no_data)
    }

    private fun getDateTime():String?{
        return try{
            val format = "MMMM d"
            val simpleDateFormat = SimpleDateFormat(format, Locale.getDefault())
            val date = Calendar.getInstance().time
            simpleDateFormat.format(date)

        }catch (e:Exception){
            e.toString()
        }
    }

    private fun getWeatherIcon(iconString:String):Int{
        return when(iconString){
            "clear-day"->{
                R.drawable.clear_day
            }
            "clear-night"->{
                R.drawable.clear_night
            }
            "cloudy"->{
                R.drawable.cloudy
            }
            "fog"->{
                R.drawable.fog
            }
            "party_cloudy_day"->{
                R.drawable.party_cloudy_day
            }
            "party-cloudy-night"->{
                R.drawable.party_cloudy_nig
            }
            "rain"->{
                R.drawable.rain
            }
            "sleet"->{
                R.drawable.sleet
            }
            "snow"->{
                R.drawable.snow
            }
            "temp"->{
                R.drawable.temp
            }
            "wind"->{
                R.drawable.wind
            }
            else -> R.drawable.clear_day
        }
    }

    fun startDailyActivity(view: View){
        //intent hacia daily activity
        val intent = Intent(this, DailyActivity::class.java)
        val array = dailyData as ArrayList<Parcelable>
        intent.putParcelableArrayListExtra(DAILY_DATA,array)
        startActivity(intent)

    }

    fun startHourlyActivity(view: View){
        val intent = Intent(this, HourlyActivity::class.java)
        val array = hourlySummary?.toTypedArray()
        intent.putExtra(HOURLY_SUMMARY,array)
        startActivity(intent)

    }


}
