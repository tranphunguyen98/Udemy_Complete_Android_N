package com.tranphunguyen.whatstheweather

import android.content.Context
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.inputmethod.InputMethod
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import com.tranphunguyen.whatstheweather.R.id.tv_result
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject
import java.io.InputStreamReader
import java.lang.Error
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLConnection
import java.net.URLEncoder
import java.util.*
import kotlin.math.log

class MainActivity : AppCompatActivity(), MyCallBack {

    val KEY = "&appid=7862c34d19303779ac58a30a74e315c1"
    val strURL = "http://api.openweathermap.org/data/2.5/weather?q="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn.setOnClickListener {

            val cityName = edt_city_name.text.toString()

            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(edt_city_name.windowToken, 0)

            Log.d("cityName", cityName)

            val cityNameEncode = URLEncoder.encode(cityName, "UTF-8")

            val url = strURL + cityNameEncode + KEY
            Log.d("cityName", url)
            OkHttpHandler(this,edt_city_name).execute(url)
        }

    }

    override fun run(result: String) {
        tv_result.text = result
    }

}
