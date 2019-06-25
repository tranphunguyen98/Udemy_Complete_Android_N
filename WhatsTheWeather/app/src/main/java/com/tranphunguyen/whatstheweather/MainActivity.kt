package com.tranphunguyen.whatstheweather

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URLEncoder

class MainActivity : AppCompatActivity(), MyCallBack {

    private val KEY = "&appid=7862c34d19303779ac58a30a74e315c1"
    private val BASEURL = "http://api.openweathermap.org"
    private val strURL = "/data/2.5/weather?q="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn.setOnClickListener {

            val cityName = edt_city_name.text.toString()

            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(edt_city_name.windowToken, 0)

            val cityNameEncode = URLEncoder.encode(cityName, "UTF-8")
            Log.d("TestRetrofit",cityNameEncode)

            val url = BASEURL + strURL + cityNameEncode + KEY

            val retrofit = Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiService =retrofit.create(APIService::class.java)


            val call = apiService.getWeather(cityNameEncode)
            call.enqueue(object : Callback<Weather> {
                override fun onResponse(call: Call<Weather>, response: Response<Weather>) {

                    val weatherJson = response.body()
//                    Log.d("TestRetrofit",response.)
                    var result = ""
                    weatherJson?.listDetailWeather?.get(0)?.let {
                        result = it.main + ":" + it.description
                        Log.d("TestRetrofit",result)
                    }

                    if(result.isNotEmpty()) {
                        tv_result.text = result
                    } else {
                        tv_result.text = "Không tìm thấy!"
                    }

                }

                override fun onFailure(call: Call<Weather>, t: Throwable) {
                    Log.d("TestRetrofit",t.message)
                }

            })


//            val moshi = Moshi.Builder().build()
//            val jsonAdapter = moshi.adapter<WeatherJson>(WeatherJson::class.java)
//
//            val weather = jsonAdapter.fromJson()



//            OkHttpHandler(this,edt_city_name).execute(url)
        }

    }

    override fun run(result: String) {
        tv_result.text = result
    }

}
