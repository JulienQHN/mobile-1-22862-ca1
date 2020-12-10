package com.example.WeatherApp

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.weather_row.view.*
import okhttp3.*
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    val api: String = "8118ed6ee68db2debfaaa5a44c832918"
    val ids: String ="id={,7778677,2960992,2962029,2965140,2966742,2964782,2965449,2961422,2965654,2962962,}"
    val metrics: String="units=metric"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        recyclerView_main.layoutManager = LinearLayoutManager(this)

        val constraintLayout = findViewById<ConstraintLayout>(R.id.layout)

        val animationDrawable = constraintLayout.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()


        fetchJsonWeather()



    }
    fun fetchJsonWeather() {

        val api = "https://api.openweathermap.org/data/2.5/group?$ids&$metrics&appid=$api"

        val request = Request.Builder().url(api).build()
        val client = OkHttpClient()

        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()
                println(body)
                val gson = GsonBuilder().create()
                val homeFeed = gson.fromJson(body, Weather::class.java)
                runOnUiThread {
                     recyclerView_main.adapter = MainAdapter(homeFeed.list) }
            }

            override fun onFailure(call: Call, e: IOException) {
                println("failed to execute the OkHttpClient request")
            }
        })
    }

}

 class MainAdapter(val list: List<WeatherStation>) : RecyclerView.Adapter<CustomViewHolder>() {

     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
         val layoutInflater = LayoutInflater.from(parent?.context)
         val cellForRow = layoutInflater.inflate(R.layout.weather_row, parent, false)
         return CustomViewHolder(cellForRow)
     }

     override fun getItemCount(): Int {
         return list.count()

     }

     override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

         holder.itemView.weatherName.text = list.get(position).name
         holder?.station = list.get(position)

     }
 }


class CustomViewHolder(view: View, var station: WeatherStation? = null) : RecyclerView.ViewHolder(view){

    companion object {

        val LOGCAT_CATEGORY = "JSON"
        val STATION_TITLE_KEY = "ActionBarTitle"
        val STATION_ID_KEY = "STATION_ID"
        val STATION_TEMPERATURE = "STATION_TEMPERATURE"
        val STATION_NAME = "STATION_NAME"
        val STATION_WEATHER_MIN = "STATION_WEATHER_MIN"
        val STATION_WEATHER_MAX = "STATION_WEATHER_MAX"
        val STATION_MINIMUM_DESCRIPTION = "STATION_MINIMUM_DESCRIPTION"
        val STATION_DESCRIPTION = "STATION_DESCRIPTION"
        val STATION_SUNRISE = "STATION_SUNRISE"
        val STATION_SUNSET = "STATION_SUNSET"
        val STATION_DT = "STATION_DT"
    }

    init {
        view.setOnClickListener {

            Log.i(LOGCAT_CATEGORY,"Recycler view Item has been clicked")
          Log.i(LOGCAT_CATEGORY, "Name is " + station?.name)
            Log.i(LOGCAT_CATEGORY, "L'id est " + station?.id)


            val intent = Intent(view.context, RecyclerDetailActivity::class.java)
            intent.putExtra(STATION_TITLE_KEY,"Weather in "+ station?.name)
            intent.putExtra(STATION_ID_KEY,station?.id)
            intent.putExtra(STATION_TEMPERATURE, station?.main?.temp+"°C")
            intent.putExtra(STATION_NAME, station?.name)
            intent.putExtra(STATION_WEATHER_MIN,"MIN : "+ station?.main?.temp_min+"°C")
            intent.putExtra(STATION_WEATHER_MAX, "MAX : "+ station?.main?.temp_max+"°C")
            intent.putExtra(STATION_MINIMUM_DESCRIPTION, station?.weather?.get(0)?.main)
            intent.putExtra(STATION_DESCRIPTION, station?.weather?.get(0)?.description?.capitalize())
            intent.putExtra(STATION_SUNRISE, station?.sys?.sunrise)
            intent.putExtra(STATION_SUNSET, station?.sys?.sunset)



            view.context.startActivity(intent)
        }

    }


}
