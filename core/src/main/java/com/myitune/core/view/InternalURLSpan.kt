package com.myitune.core.view

import android.os.SystemClock
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View

class InternalURLSpan(

    private val strURL: String,
    private val spanGentleOnClick: SpanGentleOnClickListener,

    ) : ClickableSpan() {


    override fun onClick(widget: View) {
        Log.i("InternalURLSpan", "onClick(), url: $strURL")

        spanGentleOnClick.onClick(strURL)

    }


    abstract class SpanGentleOnClickListener {

        protected open val defaultClickInterval = 500

        private var mLastClickTime: Long = 0L

        abstract fun onSingleClick(strURL: String)

        fun onClick(strURL: String) {
            val currentClickTime = SystemClock.uptimeMillis()
            val elapsedTime = currentClickTime - mLastClickTime
            //有可能2次连击，也有可能3连击，保证mLastClickTime记录的总是上次click的时间
            mLastClickTime = currentClickTime
            if (elapsedTime <= defaultClickInterval) return
            onSingleClick(strURL)
        }

    }
}