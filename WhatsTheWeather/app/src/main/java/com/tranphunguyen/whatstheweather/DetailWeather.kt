package com.tranphunguyen.whatstheweather

import com.google.gson.annotations.SerializedName

data class DetailWeather(@SerializedName("main") val main: String,
                         @SerializedName("description") val description: String)