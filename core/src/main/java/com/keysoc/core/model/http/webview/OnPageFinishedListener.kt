package com.hongyip.likon.core.model.http.webview

import android.webkit.WebView

interface OnPageFinishedListener {
    fun onPageFinished(view: WebView, url:String?)
}