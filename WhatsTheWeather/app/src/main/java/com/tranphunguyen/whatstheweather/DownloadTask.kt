package com.tranphunguyen.whatstheweather

import android.content.Context
import android.os.AsyncTask
import android.support.design.widget.Snackbar
import android.telecom.Call
import android.util.Log
import android.view.View
import com.tranphunguyen.whatstheweather.R.id.tv_result
import org.json.JSONException
import org.json.JSONObject
import java.io.InputStreamReader
import java.lang.ref.WeakReference
import java.net.HttpURLConnection
import java.net.URL

class DownloadTask(myCallBack: MyCallBack, view: View) : AsyncTask<String, Void, String>() {

    private var weakContext: WeakReference<View>? = null
    private var callBack: MyCallBack? = null

    init {
        this.weakContext = WeakReference(view)
        this.callBack = myCallBack
    }

    override fun doInBackground(vararg urls: String?): String {
        urls[0]?.let {
            return getDataFromUrl(it)
        }
        return ""
    }

    override fun onPostExecute(result: String) {
        if (result.isNotEmpty()) {
            try {
                val detail = getDetailFromData(result)

                when {
                    detail.isNotEmpty() -> callBack?.run(detail)
                    else -> callBack?.run("Không tìm thấy!")
                }

            } catch (err: JSONException) {
                err.printStackTrace()
                err.message?.let {
                    Log.d("Err", it)
                }
            }
        } else {
            callBack?.run("Không tìm thấy!")
        }
    }

    private fun getDetailFromData(data: String): String {
        var detail = ""
        val jsonObject = JSONObject(data)

        if(jsonObject.has("weather")) {
            val weatherInfo = jsonObject.getJSONArray("weather")

            for (i in 0 until weatherInfo.length()) {
                val jsonPart = weatherInfo.getJSONObject(i)

                detail = jsonPart.getString("main") + ": " + jsonPart.getString("description")
            }
        }

        return detail
    }

    private fun getDataFromUrl(url: String): String {
        var result = ""
        try {
            val urlConnection: HttpURLConnection = URL(url).openConnection() as HttpURLConnection

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
            if (err.message != null && weakContext?.get() != null) {
                Snackbar.make(weakContext?.get()!!, "Lỗi kết nối!", Snackbar.LENGTH_LONG).show()
            }
        }
        return result
    }
}