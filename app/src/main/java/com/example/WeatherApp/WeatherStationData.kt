package com.example.WeatherApp

class Weather(val list: List<WeatherStation>)

class WeatherMain (val temp: String, val temp_min: String, val temp_max: String)
class WeatherArray(val main: String, val description: String)
class WeatherStation (val weather: List<WeatherArray>, val main: WeatherMain, val id: Int, val name: String, val sys: WeatherSun, val dt: String)
class WeatherSun (val sunrise: String,  val sunset: String)

