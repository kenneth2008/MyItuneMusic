package com.myitune.core.view.dialog

import android.app.Activity
import android.app.Dialog
import android.view.View
import com.myitune.core.R

/**
 * Provide a simple way to use MessageDialog
 */

object SimpleConfirmDialog {

    const val TAG = "SimpleConfirmDialog"

    fun show(activity: Activity, stringRes: Int): Dialog? {
        return show(activity, activity.getString(stringRes))
    }

    fun show(activity: Activity?, strMessage: String): Dialog? {

        if (activity == null || activity.isFinishing) return null

        val dialog = MessageDialog.newInstance(activity)
        dialog.setMessage(strMessage)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
        return dialog
    }

    fun showWithBack(activity: Activity?, strMessage: String) {
        if (activity == null || activity.isFinishing) return

        val dialog = MessageDialog.newInstance(activity)
        dialog.setMessage(strMessage)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setOkOnClickListener(View.OnClickListener {
            activity.onBackPressed()
        })
        dialog.show()

    }

    fun show(
        activity: Activity,
        stringRes: Int,
        okOnClickListener: View.OnClickListener,
        isNeedCancelButton: Boolean,
    ) {
        show(activity,
            stringRes,
            okOnClickListener,
            R.string.verify_with_space,
            false,
            isNeedCancelButton)
    }

    @JvmOverloads
    fun show(
        activity: Activity?,
        stringRes: Int,
        okOnClickListener: View.OnClickListener?,
        resOkButton: Int = R.string.confirm,
        isCancelable: Boolean = false,
        isNeedCancelButton: Boolean = false,
    ) {
        if (activity != null && !activity.isFinishing) {
            val dialog = MessageDialog.newInstance(activity)
                .setMessage(activity.getString(stringRes))
            if (okOnClickListener != null) {
                dialog.setOkOnClickListener(okOnClickListener)
            }
            dialog.setOkButtonText(resOkButton)
                .setNeedCancelButton(isNeedCancelButton)
            dialog.setCancelable(isCancelable)
            dialog.setCanceledOnTouchOutside(isCancelable)
            dialog.show()
        }
    }

    @JvmOverloads
    fun show(
        activity: Activity?,
        stringRes: String?,
        okOnClickListener: View.OnClickListener?,
        resOkButton: Int = R.string.confirm,
        isCancelable: Boolean = false,
        isNeedCancelButton: Boolean = false,
    ) {
        if (activity != null && !activity.isFinishing) {
            val dialog = MessageDialog.newInstance(activity)
                .setMessage(
                    if (!stringRes.isNullOrEmpty()) {
                        stringRes
                    } else {
                        activity.getString(R.string.loading_error)
                    }
                )
            if (okOnClickListener != null) {
                dialog.setOkOnClickListener(okOnClickListener)
            }
            dialog.setOkButtonText(resOkButton)
                .setNeedCancelButton(isNeedCancelButton)
            dialog.setCancelable(isCancelable)
            dialog.setCanceledOnTouchOutside(isCancelable)
            dialog.show()
        }
    }
}