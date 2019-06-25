package com.tranphunguyen.whatstheweather

import com.google.gson.annotations.SerializedName

data class Weather(@SerializedName("weather") val listDetailWeather : ArrayList<DetailWeather>) {
}