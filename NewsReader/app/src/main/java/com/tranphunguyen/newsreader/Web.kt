package com.tranphunguyen.newsreader

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_web.*

class Web : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        val url = intent.getStringExtra("url")

        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()

        webView.loadData(url,"text/html","UTF-8")
    }
}
