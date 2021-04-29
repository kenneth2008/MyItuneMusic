package com.myitune.core.model.config

import android.app.Application
import android.content.Context
import android.os.Build
import android.util.Log
import com.myitune.core.BuildConfig
import com.myitune.core.model.config.language.LanguageManager


class MyApp : Application() {

//    var account: Account? = null

    lateinit var languageManager: LanguageManager
        private set


    lateinit var configManager: ConfigManager
        private set

//    lateinit var propertyManager: PropertyManager
//        private set



    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.IS_UAT) {
            Log.i(TAG, "apilevel: " + Build.VERSION.SDK_INT)
        }

        // Init Router for activity change between module
//        initRouter(this)

        // Prepare LanguageManager
        initLanguageMan()
        // Prepare ConfigManager
        configManager = ConfigManager.getInstance(this)

        // Account
//        account = Account.getAccount(this)
//        if (account != null) {
//            Translator.translate(account!!)
//        }


        /* Set current apps property */
//        propertyManager = PropertyManager(this)


        workAroundWebViewLang()
    }

    private fun initLanguageMan() {
        languageManager = LanguageManager(this)
    }

    fun logoutProcedure() {
//        account = null

        /* Clean setting to default in Config manager */
        configManager.reset(this)
    }


    private fun workAroundWebViewLang() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            WebView(this).destroy()
//            languageManager.setLangToContext(this)
//        }
    }

    companion object {
        private const val TAG = "MyApp"

        fun fromContext(context: Context): MyApp {
            return context.applicationContext as MyApp
        }
    }
}