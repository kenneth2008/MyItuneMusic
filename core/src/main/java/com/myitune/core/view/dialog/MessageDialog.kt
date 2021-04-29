package com.myitune.core.view.dialog

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.myitune.core.view.GentleOnClickListener
import com.myitune.core.view.statusbar.StatusBarMan
import com.myitune.core.R
import com.myitune.core.databinding.DialogMessageDialogBinding

/**
 * Basic MessageDialog
 */

class MessageDialog private constructor(
    private val act: Activity,
) : Dialog(act, R.style.BaseDialogTheme) {

    private var isNeedCancelButton = false

    private var okOnClickListener: View.OnClickListener? = null
    private var onCancelClickListener: View.OnClickListener? = null

    private var viewBinding: DialogMessageDialogBinding =
        DialogMessageDialogBinding.inflate(layoutInflater)

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(viewBinding.root)

        if (onCancelClickListener != null || isNeedCancelButton) {
            setupCancelButton()
        } else {
            viewBinding.btnCancel.visibility = View.GONE
        }

        viewBinding.btnConfirm.setOnClickListener(
            object : GentleOnClickListener() {
                override fun onSingleClick(v: View) {
                    Log.i(javaClass.simpleName, "onSingleClick()")
                    if (okOnClickListener != null) {
                        okOnClickListener!!.onClick(v)
                    }
                    dismiss()
                }
            }
        )
    }


    override fun onStart() {
//        setCancelable(false)
//        setCanceledOnTouchOutside(false)
        val window = window

        if (window != null) {
            val d = ColorDrawable(Color.TRANSPARENT)
            window.setBackgroundDrawable(d)
            window.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
//            window.decorView.setPadding(0)


            StatusBarMan(act).statusBarDarkTransparentMode(window)
        }

        super.onStart()
    }

    override fun onBackPressed() {
        if (isNeedCancelButton && onCancelClickListener != null) {
            onCancelClickListener!!.onClick(viewBinding.btnCancel)
            dismiss()
        } else {
            super.onBackPressed()
        }
    }

    /**
     * Replace setTitle
     *
     * @param strTitle The title string
     * @return
     */
    fun setMessageTitle(strTitle: String): MessageDialog {
        viewBinding.txtTitle.text = strTitle
        viewBinding.txtTitle.visibility = View.VISIBLE
        return this
    }

    fun setMessageTitle(resTitle: Int): MessageDialog {
        viewBinding.txtTitle.text = act.getString(resTitle)
        viewBinding.txtTitle.visibility = View.VISIBLE
        return this
    }

    fun setMessage(strMessage: String): MessageDialog {
        viewBinding.txtDesc.text = strMessage
        return this
    }

    fun setMessage(resMessage: Int): MessageDialog {
        viewBinding.txtDesc.text = act.getString(resMessage)
        return this
    }

    fun setOkOnClickListener(okOnClickListener: View.OnClickListener): MessageDialog {
        this.okOnClickListener = okOnClickListener
        return this
    }

    fun setOnCancelClickListener(onCancelClickListener: View.OnClickListener?): MessageDialog {
        this.onCancelClickListener = onCancelClickListener
        if (onCancelClickListener != null) {
            viewBinding.btnCancel.visibility = View.VISIBLE
            isNeedCancelButton = true
        }
        return this
    }

    fun setOkButtonText(okText: String): MessageDialog {
        viewBinding.btnConfirm.text = okText
        return this
    }

    fun setOkButtonText(okTextRes: Int): MessageDialog {
        return setOkButtonText(act.getString(okTextRes))
    }

    fun setCancelButtonText(cancelText: String): MessageDialog {
        viewBinding.btnCancel.text = cancelText
        return setNeedCancelButton(true)
    }

    fun setCancelButtonText(cancelTextRes: Int): MessageDialog {
        return setCancelButtonText(act.getString(cancelTextRes))
    }


    override fun setCancelable(isCancelable: Boolean) {
        super.setCancelable(isCancelable)
//        ll_dialog_rootview.setOnClickListener { dismiss() }
    }

    override fun setCanceledOnTouchOutside(isCancelable: Boolean) {
        super.setCanceledOnTouchOutside(isCancelable)
//        ll_dialog_rootview.setOnClickListener { dismiss() }
    }

    fun setNeedCancelButton(needCancelButton: Boolean): MessageDialog {
        Log.i(javaClass.simpleName, "setNeedCancelButton(), needCancelButton: $needCancelButton")
        isNeedCancelButton = needCancelButton

        if (!needCancelButton) {
            viewBinding.btnCancel.visibility = View.GONE
            return this
        }

        setupCancelButton()

        return this
    }

    private fun setupCancelButton() {
        viewBinding.btnCancel.visibility = View.VISIBLE

//        btnCancel.background = SelectorButton.buildSelectorMainButton(
//            Color.parseColor("#BFCDAD"),
//            Color.parseColor("#8E94A0"),
//            act.resources.getDimension(R.dimen.dialog_main_button_radius)
//        )

        viewBinding. btnCancel.setOnClickListener(
            object : GentleOnClickListener() {
                override fun onSingleClick(v: View) {
                    if (onCancelClickListener != null) {
                        onCancelClickListener!!.onClick(v)
                    }
                    dismiss()
                }
            }
        )
    }

    fun setDialogTypeCircleIcon(imageRes: Int): MessageDialog {
        viewBinding.imgDialogTypeIcon.setImageResource(imageRes)
        viewBinding.imgDialogTypeIcon.visibility = View.VISIBLE
        return this
    }

    override fun show() {
        if (!act.isFinishing)
            super.show()
    }

    companion object {

        fun newInstance(act: Activity): MessageDialog {
            return MessageDialog(act)
        }
    }
}