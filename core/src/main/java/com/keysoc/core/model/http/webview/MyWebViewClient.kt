package com.keysoc.core.model.http.webview

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.MailTo
import android.net.Uri
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.hongyip.likon.core.model.http.webview.OnPageFinishedListener
import com.keysoc.core.R
import com.keysoc.core.view.activity.base.BaseActivity
import java.net.MalformedURLException
import java.net.URISyntaxException
import java.net.URL
import java.util.*
import java.util.regex.Pattern


/**
 * Created by KennethTse on 23/8/2016.
 */
class MyWebViewClient(

    private val act: BaseActivity<*>,
    private val originalURL: String?,
    private val isAllowedToUseExternalBrowser: Boolean,
) : WebViewClient() {

    private var startAt: Date? = null

    var onPageFinishedListener: OnPageFinishedListener? = null
    var onOnLoadingEventListener: OnLoadingEventListener? = null

    constructor(act: BaseActivity<*>, isAllowedToUseExternalBrowser: Boolean) : this(act,
        null,
        isAllowedToUseExternalBrowser)

    override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {

        Log.i(TAG, "shouldOverrideUrlLoading(), url:$url")

        val result =  handleUrl(view, url.toString())
        Log.i(TAG, "shouldOverrideUrlLoading(), result: $result")

        return result
    }

    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
        Log.i(TAG, "shouldOverrideUrlLoading() ")
        try {
            return handleUrl(view, URL(request.url.toString()).toString())
        } catch (e: MalformedURLException) {
            e.printStackTrace()
            act.stopLoadingAction()
        }

        return true
    }

    private fun handleUrl(view: WebView, url: String): Boolean {
        Log.i(TAG, "handleUrl(), url: $url")
        when {
            MailTo.isMailTo(url) -> {
                // If email clicked
                val mt = MailTo.parse(url)
                val i = newEmailIntent(mt.to, mt.subject, mt.body, mt.cc)
                try {
                act.startActivity(i)
                } catch (e: ActivityNotFoundException) {
                    Log.e(TAG, "${e.message}")
                }
                return true
            }
            url.startsWith("tel:") -> {
                val intent = Intent(
                    Intent.ACTION_DIAL,
                    Uri.parse(url)
                )
                try {
                    act.startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    Log.e(TAG, "${e.message}")
                }
                return true
            }
            url.startsWith("intent://") -> {
                try {
                    val context = view.context
                    val intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)

                    if (intent != null) {
                        view.stopLoading()

                        val packageManager = context?.packageManager
                        val info = packageManager?.resolveActivity(
                            intent,
                            PackageManager.MATCH_DEFAULT_ONLY
                        )
                        if (info != null) {
                            context.startActivity(intent)
                        } else {
                            val fallbackUrl = intent.getStringExtra("browser_fallback_url")

                            if (!fallbackUrl.isNullOrEmpty()) {
                                view.loadUrl(fallbackUrl)
                            }
                        }

                        return true
                    }
                } catch (e: URISyntaxException) {
                    Log.e(TAG, "Can't resolve intent://", e)
                    return true
                }
            }
            url.endsWith(".pdf") -> {

                openURLWithExternalBrowser(url)

                return true
            }
            else -> {
                if (!isOriginalURL(url) && isAllowedToUseExternalBrowser) {
                    openURLWithExternalBrowser(url)
                } else {
                    onOnLoadingEventListener?.startLoading()
                    view.loadUrl(url)
                }
            }
        }
        return true
    }


    override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
        Log.i(
            TAG,
            "onPageStarted(), view:" + (view == null) + ", " + (url == null) + ", " + (favicon == null))
        super.onPageStarted(view, url, favicon)

        startAt = Date()
        Log.i(TAG, "onPageStarted(): $url")
        Log.i(TAG, "Start at:" + startAt?.time)
    }

    override fun onPageFinished(view: WebView, url: String) {
        super.onPageFinished(view, url)
        onPageFinishedListener?.onPageFinished(view, url)
    }

    private fun newEmailIntent(address: String, subject: String, body: String, cc: String): Intent {
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(address))
        intent.putExtra(Intent.EXTRA_TEXT, body)
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_CC, cc)
        intent.type = "message/rfc822"
        return intent
    }

    private fun openURLWithExternalBrowser(url: String) {
        val uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        // Create and start the chooser
        val chooser = Intent.createChooser(intent, act.getString(R.string.open_with))
        act.startActivity(chooser)
    }

    private fun isOriginalURL(url: String): Boolean {
        return url.isNotEmpty() && originalURL?.isNotEmpty() == true && originalURL == url
    }

    private fun isYouTube(url: String): Boolean {
        val patten =
            "/^(?:https?://)?(?:www\\.)?youtu(?:\\.be|be\\.com)/(?:watch\\?v=)?([\\w-]{10,})/"
        val pattern = Pattern.compile(patten)

        val matcher = pattern.matcher(url)
        return matcher.matches()
    }

    companion object {
        const val TAG = "MyWebViewClient"
    }

    override fun onLoadResource(view: WebView?, url: String?) {
        if (url != null && url.endsWith(".pdf")) {
            openURLWithExternalBrowser(url)
        } else super.onLoadResource(view, url)
    }
}
