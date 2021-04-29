package com.myitune.core.view.dialog

import android.app.Activity
import android.app.Dialog
import android.util.Log
import android.view.View
import com.myitune.core.R

/**
 *Created by chrisyeung on 5/9/2019.
 */
object TitleConfirmDialog {

    fun show(activity: Activity, msgRes: Int, titleRes: Int): Dialog? {
        Log.i(javaClass.simpleName, "show1()")
        return show(activity, activity.getString(msgRes), activity.getString(titleRes))
    }

    fun show(activity: Activity, msgString: String, titleString: String?): Dialog? {

        if (activity.isFinishing) return null

        val dialog = MessageDialog.newInstance(activity)
        dialog.setMessage(msgString)
        if (titleString != null) {
            dialog.setMessageTitle(titleString)
        }
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()

        return dialog
    }

    fun show(
        activity: Activity?,
        msgRes: Int?,
        titleRes: Int?,
        okOnClickListener: View.OnClickListener?,
        resOkButton: Int = R.string.confirm,
        isCancelable: Boolean = true,
        isNeedCancelButton: Boolean = true,
    ): Dialog? {
        Log.i(javaClass.simpleName, "show3()")
        if (activity == null) return null

        return show(activity,
            if (msgRes != null) activity.getString(msgRes) else null,
            if (titleRes != null) activity.getString(titleRes) else null,
            okOnClickListener, resOkButton, isCancelable, isNeedCancelButton)

    }


    fun show(
        activity: Activity?,
        msgString: String?,
        titleString: String?,
        okOnClickListener: View.OnClickListener?,
        resOkButton: Int = R.string.confirm,
        isCancelable: Boolean = true,
        isNeedCancelButton: Boolean = true,
    ): Dialog? {

        if (activity == null || activity.isFinishing) return null

        val dialog = MessageDialog.newInstance(activity)
        if (msgString != null) {
            dialog.setMessage(msgString)
        }
        if (okOnClickListener != null) {
            dialog.setOkOnClickListener(okOnClickListener)
        }
        if (titleString != null) {
            dialog.setMessageTitle(titleString)
        }
        dialog.setOkButtonText(resOkButton)
            .setNeedCancelButton(isNeedCancelButton)
        dialog.setCancelable(isCancelable)
        dialog.setCanceledOnTouchOutside(isCancelable)
        dialog.show()

        return dialog

    }
}