package com.tranphunguyen.whatstheweather

import android.content.Context
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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

class MainActivity : AppCompatActivity() {

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
            DownloadTask().execute(url)
        }

    }

    inner class DownloadTask : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg urls: String?): String {

            var result = ""
            val url: URL
            val urlConnection: HttpURLConnection

            try {
                url = URL(urls[0])
                urlConnection = url.openConnection() as HttpURLConnection
                Log.d("Test", urlConnection.responseCode.toString())
                if (urlConnection.responseCode == 200) {
                    val inputStream = urlConnection.inputStream

                    val reader = InputStreamReader(inputStream)

                    var data = reader.read()

                    while (data != -1) {

                        val currentChar = data.toChar()

                        result += currentChar

                        data = reader.read()

                    }
                }
            } catch (err: Exception) {
                err.printStackTrace()
                err.message?.let {
                    //                    showToastErr(this@MainActivity,it)
                    Log.d("Err", it)
                }
            }

            return result
        }

        override fun onPostExecute(result: String?) {
            Log.d("Result", result)
            if (result!!.isNotEmpty()) {
                try {

                    val jsonObject = JSONObject(result)

                    val weatherInfo = jsonObject.getJSONArray("weather")
                    var detail = ""
                    for (i in 0 until weatherInfo.length()) {
                        val jsonPart = weatherInfo.getJSONObject(i)

                        detail = jsonPart.getString("main") + ": " + jsonPart.getString("description")
                    }
                    if (detail.isNotEmpty()) {
                        tv_result.text = detail
                    } else {
                        tv_result.text = "Không tìm thấy!"
//                    showToastErr(this@MainActivity,"Không tìm thấy!")
                        Log.d("Err", "Không tìm thấy!")
                    }

                } catch (err: JSONException) {
                    err.printStackTrace()
                    err.message?.let {
                        //                    showToastErr(this@MainActivity,it)
                        Log.d("Err", it)
                    }
                }
            } else {
                tv_result.text = "Không tìm thấy"
            }
        }
    }

    private fun showToastErr(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}
