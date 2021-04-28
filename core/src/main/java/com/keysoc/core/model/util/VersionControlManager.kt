package com.keysoc.core.model.util

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import com.keysoc.core.view.activity.base.BaseActivity
import com.hongyip.likon.core.view.activity.base.SimpleActionListener
import com.keysoc.core.R
import com.keysoc.core.view.dialog.MessageDialog
import com.keysoc.core.BuildConfig


/**
 * Created by KennethTse on 12/6/2017.
 */

class VersionControlManager(
    private val act: BaseActivity<*>,
    private val handler: SimpleActionListener?,
) {

    companion object {
        private const val TAG = "VersionControlManager"
    }

    fun checkVersion() {

        Log.i(TAG, "checkVersion()")
        val version = BuildConfig.VERSION_CODE.toFloat()
        val configManager = act.myApp.configManager
        Log.i(TAG, "checkVersion(), version:" + version + ", lastver:" + configManager.lastver)

        if (configManager.isForceUpdate || configManager.lastver != null && configManager.lastver!! > version) {

            if (!configManager.applink.isNullOrEmpty()) {
                suggestedUpdate(configManager.applink!!, configManager.isForceUpdate)
                return
            }

        }

        /* Terminate the update process if no need to update */
        handler?.onFinished()
    }

    private fun suggestedUpdate(appLink: String, isForcesUpdate: Boolean) {
        Log.i(TAG, "suggestedUpdate(), isForcesUpdate:$isForcesUpdate")

        /* Terminate the update process if is reminded today (if is not force update) */
        if (!isForcesUpdate) {
            // Check if already alert in 24 hours
            val isUpdatedToday = UpdateReminderManager().isSuggesttedToUpdateInTime(act.myApp)
            Log.i(TAG, "suggestedUpdate(), isUpdatedToday:$isUpdatedToday")
            if (isUpdatedToday) {
                handler?.onFinished()
                return
            }
        }

        if (act.isFinishing) return

        val dialog = MessageDialog.newInstance(act)
            .setMessage(if (isForcesUpdate) R.string.force_update else R.string.suggest_update)
            .setOkButtonText(R.string.update_now)
            .setOkOnClickListener(View.OnClickListener {
                // Open app link
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(appLink)
                act.startActivity(intent)

                // And reopen the dialog
                if (isForcesUpdate) {
                    suggestedUpdate(appLink, true)
                } else {
                    UpdateReminderManager().saveUpdateReminder(act)

                }
            })
        dialog.setCancelable(!isForcesUpdate)
        dialog.setCanceledOnTouchOutside(!isForcesUpdate)

        if (!isForcesUpdate) {
            dialog.setCancelButtonText(R.string.update_postpone)
            dialog.setOnCancelClickListener(View.OnClickListener {
                UpdateReminderManager().saveUpdateReminder(act)
                handler?.onFinished()

            })
        }

        act.stopLoadingAction()
        dialog.show()

    }

}
