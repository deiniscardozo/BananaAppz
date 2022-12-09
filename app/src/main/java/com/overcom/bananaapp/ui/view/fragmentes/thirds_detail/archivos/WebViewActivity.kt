package com.overcom.bananaapp.ui.view.fragmentes.thirds_detail.archivos

import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity


class WebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val myWebView = WebView(this)
        setContentView(myWebView)
        myWebView.settings.javaScriptEnabled = true

        myWebView.loadUrl(intent.getStringExtra("url").toString())
    }

}