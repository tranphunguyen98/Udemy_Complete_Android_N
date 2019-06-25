package com.tranphunguyen.whatstheweather

import android.os.AsyncTask
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.View
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONException
import org.json.JSONObject
import java.lang.ref.WeakReference

class OkHttpHandler(myCallBack: MyCallBack,view: View) : AsyncTask<String, Void, String>() {

    private var weakContext: WeakReference<View>? = null
    private var callBack: MyCallBack? = null

    init {
        this.weakContext = WeakReference(view)
        this.callBack = myCallBack
    }

    override fun doInBackground(vararg params: String?): String? {
        params[0]?.let {
           return getDataFromUrl(it)
        }

        return ""
    }

    override fun onPostExecute(result: String) {
        Log.d("TestResult",result)
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
        val weather = Gson().fromJson(data,Weather::class.java)

        val detailWeather : DetailWeather? = weather?.listDetailWeather?.get(0)
        detailWeather?.let {
            return it.main + " : " + it.description
        }

        return ""
    }

    private fun getDataFromUrl(url: String): String {
        val okHttpClient = OkHttpClient()
        val builder = Request.Builder()
        var result = ""

        builder.url(url)

        val request = builder.build()

        try {
            val response = okHttpClient.newCall(request).execute()

            response.body()?.string()?.let {
                result = it
            }

            weakContext?.get()?.let {
                Snackbar.make(it, "Result", Snackbar.LENGTH_LONG)
            }

        } catch (e: Exception) {
            weakContext?.get()?.let {
                Snackbar.make(it, "Lỗi kết nối!", Snackbar.LENGTH_INDEFINITE).show()
            }
        }
        weakContext?.get()?.let {
            Snackbar.make(it, result, Snackbar.LENGTH_INDEFINITE).show()
        }
        return result
    }
}