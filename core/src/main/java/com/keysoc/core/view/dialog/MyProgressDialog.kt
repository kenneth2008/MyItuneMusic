package com.keysoc.core.view.dialog

/**
 * Created by KennethTse on 9/11/2017.
 */

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import android.view.Window
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable.INFINITE
import com.keysoc.core.view.activity.base.BaseActivity
import com.keysoc.core.R
import com.keysoc.core.databinding.LayoutLoadingDialogBinding
import com.keysoc.core.view.statusbar.StatusBarMan

open class MyProgressDialog(
    protected val act: BaseActivity<*>,
    theme: Int,
) : Dialog(act, theme) {

    private val viewBinding: LayoutLoadingDialogBinding =
        LayoutLoadingDialogBinding.inflate(layoutInflater)

    var isCancelableSwitch: Boolean = false

    init {

        requestWindowFeature(Window.FEATURE_NO_TITLE)

        setContentView(viewBinding.root)

        val animationView: LottieAnimationView = viewBinding.animationView
        animationView.setAnimation("loading.json")
        animationView.imageAssetsFolder = "loading/"
        animationView.repeatCount = INFINITE
        animationView.playAnimation()
    }

    override fun onStart() {

        setCancelable(isCancelableSwitch)
        setCanceledOnTouchOutside(false)

        val d = ColorDrawable(0x75000000)
        window?.setBackgroundDrawable(d)
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
//        window.decorView.setPadding(0)


        if (window != null) {
            StatusBarMan(act).statusBarDarkTransparentMode(window!!)
        }

//        if(isShowMobilePass){
//            btnOpenQRCode.visibility = View.VISIBLE
//            btnOpenQRCode.setOnClickListener {
//                // Show Mobile Pass Dialog
//            }
//        }else{
//            btnOpenQRCode.visibility = View.GONE
//        }

        super.onStart()
    }

//    fun setMessage(strMessage: String): MyProgressDialog {
//        if (txtSmartHome != null) {
//            txtSmartHome.text = strMessage
//        }
//        return this
//    }

    companion object {

        fun createDialog(act: BaseActivity<*>): MyProgressDialog? {


            return if (act.isFinishing) null else {
                val customProgressDialog = MyProgressDialog(act, R.style.BaseDialogTheme)

                customProgressDialog
            }
        }
    }

}