package com.itcrowdarg.unsplash

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.JavascriptInterface
import kotlinx.android.synthetic.main.activity_main.*
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    // Life Cycle
    // ------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setup webview
        setupWebView()
        // Start to load the site
        loadSite()
    }


    // Helpers
    // ---------------

    // Load required page
    private fun loadSite() {
        // Load the page in the webview
        webView.loadUrl("file:///android_asset/${getString(R.string.page_name)}")
    }

    // Setup webview settings
    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        // Enable JavaScript and set some more configuration
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true
        webSettings.loadWithOverviewMode = true
        webSettings.useWideViewPort = true
        webSettings.builtInZoomControls = false
        webSettings.displayZoomControls = false
        webSettings.setSupportZoom(false)
        webSettings.defaultTextEncodingName = "utf-8"

        // Set custom webview client
        webView.webViewClient = object : WebViewClient() {

            // Detect when the web has finished
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                // Set the initial image
                setImageUrlJS()
            }
        }

        // Add message handler
        webView.addJavascriptInterface(JSNativeAppInterface(this), "nativeAppInterface")
    }

    // This method will call the JS to set the image in the page
    private fun setImageUrlJS() {
        val randomized = Random.nextDouble()
        webView.evaluateJavascript("javascript:setImageUrl('${getString(R.string.random_photo_url)}?random=$randomized');", null);
    }

    // WebView Message Handler
    // ----------------------------

    // This class will create an interface in JS to handle the messages for refresh
    inner class JSNativeAppInterface(private val context: Activity) {

        @JavascriptInterface
        fun postMessage(message:String) {
            // Received message from webview in native, process data
            // Check the message, in this case it will be always refresh but just make sure
            if (message == context.getString(R.string.js_message_refresh)) {
                // Do refresh the content
                context.runOnUiThread {
                    setImageUrlJS()
                }
            }
            else {
                context.runOnUiThread {
                    Toast.makeText(
                        context,
                        "We did receive a different message from refresh interface",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}
