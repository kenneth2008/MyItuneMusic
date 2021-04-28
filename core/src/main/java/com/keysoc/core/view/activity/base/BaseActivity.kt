package com.keysoc.core.view.activity.base

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.viewbinding.ViewBinding
import com.keysoc.core.model.config.MyApp
import com.hongyip.likon.core.view.activity.base.MyContextWrapper
import com.keysoc.core.R
import com.keysoc.core.view.GentleOnClickListener
import com.keysoc.core.view.dialog.MyProgressDialog
import com.keysoc.core.view.statusbar.StatusBarMan
import java.lang.reflect.ParameterizedType


abstract class BaseActivity<T : ViewBinding> : AppCompatActivity() {

    lateinit var myApp: MyApp

    protected var myProgressDialog: MyProgressDialog? = null

    protected var isComeFromNavigation: Boolean = false
    private var lastBackOnClick = 0L

    protected var txtActionBarTitle: TextView? = null

    protected open val actionBarTitleRes: Int = 0
    protected open val actionBarLeftImageButtonRes: Int = 0

    protected var btnBack: ImageView? = null
    private var backBtnOnClick = object: GentleOnClickListener() {
        override fun onSingleClick(v: View) {
            onBackPressed()
        }
    }

    protected open val isDarkStatusBar = false

    var isLoading = false

    protected lateinit var viewBinding: T

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        myApp = application as MyApp

//        myApp.languageManager.setLanguageToAllContext(this)


        //利用反射，调用指定ViewBinding中的inflate方法填充视图
        val type = javaClass.genericSuperclass
        if (type is ParameterizedType) {
            val clazz = type.actualTypeArguments[0] as Class<T>
            val method = clazz.getMethod("inflate", LayoutInflater::class.java)
            viewBinding = method.invoke(null, layoutInflater) as T
            setContentView(viewBinding.root)
        }

        handleExtra()
    }

    private fun handleExtra() {
        /* Get NoticeObj by notice id */
        val intent = intent
        if (intent != null) {
            val data = intent.extras
            if (data != null) {
                isComeFromNavigation = data.getBoolean(EXTRA_IS_COME_FROM_NAVIGATION, false)
            }
        }
    }

    protected open fun setupActionBar() {

        txtActionBarTitle = findViewById(R.id.txtActionBarTitle)
        btnBack = findViewById(R.id.btnBack)

        handleCutOut()

        Log.i(TAG, "setActionBarTitle(), actionBarTitleRes: $actionBarTitleRes")
        /* set ActionBarTitle */
        if (actionBarTitleRes != 0) {
            txtActionBarTitle?.text = getString(actionBarTitleRes)
            txtActionBarTitle?.visibility = View.VISIBLE
        } else {
            txtActionBarTitle?.visibility = View.GONE
        }

        /* set Back Image */
        if (actionBarLeftImageButtonRes != 0) {
            btnBack?.setImageResource(actionBarLeftImageButtonRes)
        }

        if (isDarkStatusBar)
            StatusBarMan(this).statusBarDarkTransparentMode()
        else
            StatusBarMan(this).statusBarLightTransparentMode()


        btnBack?.setOnClickListener(backBtnOnClick)
    }


    open fun handleCutOut(actionBarParam: View? = null) {
        val actionBar: View = actionBarParam ?: findViewById(R.id.action_bar_margin_container)
        ?: return

        var statusBarHeight = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = resources.getDimensionPixelSize(resourceId)
        }

        actionBar.setPadding(0, statusBarHeight, 0, actionBar.paddingBottom)
    }

    protected abstract fun setupBodyUI()



    fun logEvent(category: String, action: String, label: String? = null) {
//        AnalyticsManager.logEvent(myApp, category, action, label)
    }

    fun setBackgroundResForEditText(editText: AppCompatEditText, backgroundRes: Int) {
        val paddingTop = editText.paddingTop
        val paddingBottom = editText.paddingBottom
        val paddingLeft = editText.paddingLeft
        val paddingRight = editText.paddingRight
        editText.setBackgroundResource(backgroundRes)
        editText.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom)
    }

    /**
     * Dialog Box
     */
    // Loading Dialog
    open fun startLoading() {
//        Log.i(TAG, "startLoading()")
        startLoading(0, false)
    }

    open fun startLoading(res: Int?, cancelable: Boolean = false) {
        Log.i(TAG, "startLoading()")
        if (myProgressDialog == null && !isFinishing) {
            myProgressDialog = MyProgressDialog.createDialog(this)
//            if(res!=null) {
//                myProgressDialog?.setMessage(getString(res))
//            }
            myProgressDialog?.isCancelableSwitch = cancelable
            myProgressDialog?.show()
        }
    }

//    /**
//     * Stop loading with time delay
//     * @param mOnStopLoadingListener
//     */
//    fun stopLoading(mOnStopLoadingListener: OnStopLoadingListener?) {
//
//        this.onStopLoadingListener = mOnStopLoadingListener
//
//        if (onStopLoadingListener == null) {
//            stopLoadingAction()
//        } else {
//            // Do update after 500
//            window.decorView.postDelayed({
//                if (!this@BaseActivity.isFinishing) {
//                    stopLoadingAction()
//                }
//            }, 300)
//        }
//    }

    /**
     * The solid stop loading action without time delay
     */
    open fun stopLoadingAction() {
        if (myProgressDialog != null && !isFinishing) {
            Log.i(TAG, "stopLoadingAction()")
            myProgressDialog!!.dismiss()
            isLoading = false
            myProgressDialog = null
        }
    }


    /**
     * Sort cut to hide keyboard
     * @param view
     */
    fun hideKeyBoard(view: View?) {
        if (!isFinishing && view != null) {
            // Hide Keyboard
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun attachBaseContext(newBase: Context) {
        val context = MyContextWrapper(newBase).wrap()
        super.attachBaseContext(context)
    }

    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
        enterFromRightAnimation()
    }

    fun enterFromRightAnimation() {
//        overridePendingTransition(R.anim.animation_enter_from_right, R.anim.animation_none)
    }

    fun leaveToRightAnimation() {
//        overridePendingTransition(R.anim.animation_none, R.anim.animation_leave_to_right)
    }

    fun enterFromBottomAnimation() {
//        overridePendingTransition(R.anim.animation_enter_bottom, R.anim.animation_none)
    }

    fun leaveToBottomAnimation() {
//        overridePendingTransition(R.anim.animation_none, R.anim.animation_leave_bottom)
    }

    fun backToExit() {
        val now = System.currentTimeMillis()
        if (now - lastBackOnClick > 2000) {
            lastBackOnClick = now
            Toast.makeText(this, R.string.press_again_to_exit, Toast.LENGTH_SHORT).show()
        } else {
            finish()
            leaveToBottomAnimation()
        }
    }

    public override fun onResume() {
        super.onResume()
    }

    /**
     * Most of the activity should use leaveToRightAnimation
     */
    override fun onBackPressed() {
        super.onBackPressed()
        leaveToRightAnimation()
    }


    companion object {

        private const val TAG = "BaseActivity"

        const val EXTRA_IS_COME_FROM_NAVIGATION = "NAVIGATION"
    }
}