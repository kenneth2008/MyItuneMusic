package com.hongyip.likon.core.view.activity.base

import android.content.Context
import android.content.ContextWrapper
import com.myitune.core.model.config.MyApp

class MyContextWrapper(private val mContext: Context) : ContextWrapper(mContext) {

    fun wrap(): Context {

        val myApp = mContext.applicationContext as MyApp
//        val context = myApp.languageManager.setLangToContext(mContext)

       return ContextWrapper(myApp)
    }

}