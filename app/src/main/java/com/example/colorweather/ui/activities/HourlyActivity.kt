package com.example.colorweather.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.colorweather.R
import kotlinx.android.synthetic.main.activity_hourly.*

class HourlyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hourly)

        //val array = ArrayList<String>(arrayListOf("Nov 8 11pm, Nublado", "Nov 8 12pm, Despejado", "Nov 8 1pm, Despejado"))


        val summary = intent.getStringArrayExtra(MainActivity.HOURLY_SUMMARY)


        val adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,summary)

        hourlyWeatherList.adapter = adapter





    }
}
