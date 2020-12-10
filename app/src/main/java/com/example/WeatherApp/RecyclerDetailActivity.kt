package com.example.WeatherApp

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager

import kotlinx.android.synthetic.main.activity_recycler_detail.*



class RecyclerDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_detail)

        val constraintLayout = findViewById<ConstraintLayout>(R.id.layout)
        val animationDrawable = constraintLayout.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()


        RecyclerViewDetails.layoutManager = LinearLayoutManager(this)
        val title = intent.getStringExtra(CustomViewHolder.STATION_TITLE_KEY)
        supportActionBar?.title = title
        STATION_TEMPERATURE.text = intent.getStringExtra(CustomViewHolder.STATION_TEMPERATURE)
        STATION_NAME.text = intent.getStringExtra(CustomViewHolder.STATION_NAME)
        STATION_WEATHER_MIN.text = intent.getStringExtra(CustomViewHolder.STATION_WEATHER_MIN)
        STATION_WEATHER_MAX.text = intent.getStringExtra(CustomViewHolder.STATION_WEATHER_MAX)
        // STATION_MINIMUM_DESCRIPTION.text = intent.getStringExtra(CustomViewHolder.STATION_MINIMUM_DESCRIPTION)
        STATION_DESCRIPTION.text = intent.getStringExtra(CustomViewHolder.STATION_DESCRIPTION)

    }


}
