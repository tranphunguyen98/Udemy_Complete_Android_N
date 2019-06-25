package com.tranphunguyen.whatstheweather

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private val KEY = ""
private val strURL = ""

interface APIService {
    @GET("/data/2.5/weather?appid=7862c34d19303779ac58a30a74e315c1")
    fun getWeather(@Query("q",encoded = true) city : String) : Call<Weather>
}