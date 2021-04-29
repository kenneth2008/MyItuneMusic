package com.myitune.core.view

import android.os.SystemClock
import android.view.View

/**
 * Version 2 @ 24-12-2012
 */
abstract class GentleOnClickListener : View.OnClickListener {

    protected open val defaultClickInterval = 500

    private var mLastClickTime: Long = 0

    /**
     * click响应函数
     * @param v The view that was clicked.
     */
    abstract fun onSingleClick(v: View)

    override fun onClick(v: View) {
        val currentClickTime = SystemClock.uptimeMillis()
        val elapsedTime = currentClickTime - mLastClickTime
        //有可能2次连击，也有可能3连击，保证mLastClickTime记录的总是上次click的时间
        mLastClickTime = currentClickTime
        if (elapsedTime <= defaultClickInterval) return
        onSingleClick(v)
    }
}