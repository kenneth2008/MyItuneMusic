package com.myitune.music.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.myitune.core.view.activity.base.BaseActivity
import com.myitune.music.BuildConfig
import com.myitune.music.R
import com.myitune.music.databinding.ActivityBeforeloginSplashscreenBinding
import com.myitune.music.view.home.HomeAct
import java.util.*

class SplashScreenAct : BaseActivity<ActivityBeforeloginSplashscreenBinding>() {

    private val TAG = "SplashScreenAct"

    private var timeOfStart: Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterFromBottomAnimation()

        timeOfStart = Calendar.getInstance().timeInMillis


        setupActionBar()
        setupBodyUI()
    }


    override fun setupBodyUI() {
        Log.i(TAG, "setupBodyUI()")

        viewBinding.lblVersion.text =
            getString(R.string.splashscreen_version_format, BuildConfig.VERSION_NAME)

//        SimpleConfirmDialog.show(this, "Yeah!")

        goToNextPage()
    }

    private fun goToNextPage() {

        /* Language */
//        val languageManager: LanguageManager = myApp.languageManager
//        if (!languageManager.hasSetLang()) {
//            languageManager.setLang(LANG.LANG_ZH)
//            myApp.languageManager.setLanguageToAllContext(this)
//
//        }
//
//
//        val configManager: ConfigManager = myApp.configManager
//
//        /* Agreement */
//        if (!configManager.hasReadTNC()) {
//            Log.i(TAG, "goToNextPage(), hasReadHowItWorks():" + configManager.hasReadTNC())
//            val intent = Intent(this, AgreementAct::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
//            myStartActivity(intent)
//            return
//        }
//
//
//        if (!myApp.configManager.isLoggedIn) {
//            Log.i(TAG, "goToNextPage(), hasLoggedIn():" + myApp.configManager.isLoggedIn)
//            val intent = Intent(this, LoginAct::class.java)
//            myStartActivity(intent)
//            return
//        }

        Log.i(TAG, "goToNextPage(), Default()")
        handleExtra()
    }

    private fun handleExtra() {

        /**
         * Important to reset timer at first load
         */
//        GetLandingManager.resetTimer(this)

        myStartHomeActivity()
    }

    private fun myStartHomeActivity() {
        Log.i(TAG, "myStartHomeActivity()")

        val calendar = Calendar.getInstance()
        if (calendar.timeInMillis - timeOfStart > 500L) {

            if (!isExecutedOnce) {
                isExecutedOnce = true
                Log.e(TAG, "startActivity(), time to go next page")

                val intent = Intent(this, HomeAct::class.java)
                startActivity(intent)
                this@SplashScreenAct.finish()
                enterFromRightAnimation()
            }
        } else {
            window.decorView.postDelayed({
                myStartHomeActivity()
            }, calendar.timeInMillis - timeOfStart + 100L)
        }
    }


    private var isExecutedOnce: Boolean = false

    private fun myStartActivity(intent: Intent?, isEnterFromBottom: Boolean = false) {
        val calendar = Calendar.getInstance()
        if (calendar.timeInMillis - timeOfStart > 500L) {

            if (!isExecutedOnce) {
                isExecutedOnce = true
                Log.e(TAG, "startActivity(), time to go next page")
                startActivity(intent)
                finish()

                if (isEnterFromBottom) {
                    enterFromBottomAnimation()
                } else {
                    enterFromRightAnimation()
                }
            }
        } else {
            viewBinding.imgIcon.postDelayed({
                myStartActivity(intent)
            }, calendar.timeInMillis - timeOfStart + 100L)
        }
    }

    override fun onBackPressed() {
        backToExit()
    }

}