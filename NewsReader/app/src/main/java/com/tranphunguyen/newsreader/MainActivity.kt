package com.tranphunguyen.newsreader

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    val list = ArrayList<News>()

    val URL = "https://hacker-news.firebaseio.com/v0/newstories.json"

    lateinit var adapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        newsRecyclerview.layoutManager = LinearLayoutManager(this)

        adapter = NewsAdapter(list)

        newsRecyclerview.adapter = adapter

        val newItemHandler = Handler { msg ->
            Log.d("TestResult1", msg.obj as String)
            if (msg.obj as String != "null") {
                val jsonObject = JSONObject(msg.obj as String)

                val title = jsonObject.getString("title")
                if (jsonObject.has("url")) {
                    val url = jsonObject.getString("url")
                    list.add(News(title, url))
                    adapter.notifyDataSetChanged()
                }
            }

            true
        }

        val mHandler = Handler { msg ->
            if (msg.what == 0) {
                Log.d("TestResult1", msg.obj as String)

                val listNews = JSONArray(msg.obj as String)

                for (i in 0..20) {
                    Log.d("TestResult2", listNews.getString(i))

                    Thread(Runnable {
                        val okHttpClient = OkHttpClient()
                        val builder = Request.Builder()
                        var result = ""

                        builder.url("https://hacker-news.firebaseio.com/v0/item/${listNews.getString(i)}.json")

                        val request = builder.build()

                        try {

                            val response = okHttpClient.newCall(request).execute()

                            response.body?.string()?.let {
                                result = it
                            }

                        } catch (e: Exception) {
                            e.message?.let {
                                Log.d("TestResult", it)
                            }
                            Log.d("TestResult", "Lỗi rồi!")
                            e.printStackTrace()
                        }

                        val message = Message()
                        message.what = 0
                        message.obj = result
                        newItemHandler.sendMessage(message)
                    }).start()
                }
            }
            true
        }

        Thread(object : Thread() {
            override fun run() {
                val okHttpClient = OkHttpClient()
                val builder = Request.Builder()
                var result = ""

                builder.url(URL)

                val request = builder.build()

                try {

                    val response = okHttpClient.newCall(request).execute()

                    response.body?.string()?.let {
                        result = it
                    }

                } catch (e: Exception) {
                    e.message?.let {
                        Log.d("TestResult", it)
                    }
                    e.printStackTrace()
                }

                val message = Message()
                message.what = 0
                message.obj = result
                mHandler.sendMessage(message)
            }

        }).start()


    }
}
