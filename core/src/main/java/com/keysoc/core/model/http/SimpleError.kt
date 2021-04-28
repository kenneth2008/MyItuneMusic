package com.keysoc.core.model.http

import android.view.View
import com.keysoc.core.model.http.response.BaseRespond
import com.keysoc.core.view.activity.base.BaseActivity
import com.keysoc.core.R
import com.keysoc.core.view.dialog.MessageDialog

/**
 * Created by KennethTse on 11/2/2017.
 */

class SimpleError {

    companion object {

        const val TAG = "SimpleError"

        fun show(activity: BaseActivity<*>, defaultMessageRes: Int) {
            show(activity, null, defaultMessageRes)
        }

        fun show(
            activity: BaseActivity<*>,
            respond: BaseRespond? = null,
            defaultMessageRes: Int = 0,
        ) {

            var strErr: String? = null

            /* Check Network */
            if (!NetworkUtil.isNetworkAvailable(activity)) {
                strErr = activity.getString(R.string.no_internet_connection)
            }
            /* if still null, use response error */
//            if (strErr.isNullOrEmpty() && respond != null) {
//                Translator.translate(respond)
//                strErr = respond.getErrMsg(activity.myApp)
//            }

            /* if still null, use default message */
            if (strErr.isNullOrEmpty()) {
                strErr =
                    activity.getString(if (defaultMessageRes == 0) R.string.loading_error else defaultMessageRes)
            }


            val dialog = MessageDialog.newInstance(activity)
            dialog.setMessage(strErr)
            dialog.setCancelable(false)
            dialog.setDialogTypeCircleIcon(R.drawable.alertbox_2_icn)
            dialog.setCanceledOnTouchOutside(false)
            dialog.setOkButtonText(R.string.confirm)
            dialog.setNeedCancelButton(false)

            if (!activity.isFinishing) dialog.show()
        }


        /**
         * Same function, run with String, not string resource
         *
         * @param activity
         */
        fun show(activity: BaseActivity<*>, strMessage: String?) {
            show(activity, null, strMessage)
        }

        fun show(activity: BaseActivity<*>, respond: BaseRespond?, strMessage: String?) {

            var strErr: String? = null

            /* Check Network */
            if (!NetworkUtil.isNetworkAvailable(activity)) {
                strErr = activity.getString(R.string.no_internet_connection)
            }
            /* if still null, use response error */
//            if (strErr.isNullOrEmpty() && respond != null) {
//                strErr = respond.errMsg
//            }
            /* if still null, use default message */
            if (strErr.isNullOrEmpty()) {
                strErr =
                    if (strMessage.isNullOrEmpty()) activity.getString(R.string.loading_error) else strMessage
            }


            val dialog = MessageDialog.newInstance(activity)
            dialog.setMessage(strErr)
            dialog.setCancelable(false)
            dialog.setCanceledOnTouchOutside(false)
            dialog.setDialogTypeCircleIcon(R.drawable.alertbox_2_icn)

            if (!activity.isFinishing) dialog.show()
        }

        fun show(
            act: BaseActivity<*>,
            okOnClickListener: View.OnClickListener?,
            resOkButton: Int = R.string.confirm,
            cancelClickListener: View.OnClickListener?,
            resCancelButton: Int = R.string.cancel_with_space,
        ) {
            show(
                act,
                null,
                null,
                okOnClickListener,
                resOkButton,
                cancelClickListener,
                resCancelButton
            )
        }

        fun show(
            act: BaseActivity<*>, strMessage: String?,
            okOnClickListener: View.OnClickListener?,
        ) {
            show(
                act, null, strMessage, okOnClickListener, R.string.confirm,
                null, 0
            )
        }

        fun show(
            act: BaseActivity<*>, respond: BaseRespond?, strMessage: String?,
            okOnClickListener: View.OnClickListener?,
        ) {
            show(
                act, respond, strMessage, okOnClickListener, R.string.confirm,
                null, 0
            )
        }

        fun show(
            act: BaseActivity<*>,
            respond: BaseRespond?,
            strMessage: String?,
            okOnClickListener: View.OnClickListener?,
            resOkButton: Int = R.string.confirm,
            cancelClickListener: View.OnClickListener?,
            resCancelButton: Int = R.string.cancel_with_space,
        ) {

            var strErr: String? = null

            /* Check Network */
            if (!NetworkUtil.isNetworkAvailable(act)) {
                strErr = act.getString(R.string.no_internet_connection)
            }
            /* if still null, use response error */
//            if (strErr.isNullOrEmpty() && respond != null) {
//                strErr = respond.errMsg
//            }
            /* if still null, use default message */
            if (strErr.isNullOrEmpty()) {
                strErr =
                    if (strMessage.isNullOrEmpty()) act.getString(R.string.loading_error) else strMessage
            }

            val dialog = MessageDialog.newInstance(act)
                .setMessage(strErr)
                .setDialogTypeCircleIcon(R.drawable.alertbox_2_icn)

            if (okOnClickListener != null) {
                dialog.setOkButtonText(resOkButton)
                dialog.setOkOnClickListener(okOnClickListener)
            }


            dialog.setCanceledOnTouchOutside(false)

            if (cancelClickListener != null) {
                dialog.setCancelButtonText(resCancelButton)
                dialog.setOnCancelClickListener(cancelClickListener)
                dialog.setNeedCancelButton(true)
                dialog.setCancelable(true)
            } else {
                dialog.setCancelable(false)
            }

            act.stopLoadingAction()
            if (!act.isFinishing) dialog.show()
        }
    }
}