package com.myitune.core.model.util

import android.content.Context
import android.util.Log
import com.myitune.datamodel.sharedpref.SharedPref


import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by KennethTse on 26/5/2017.
 */

class UpdateReminderManager {

    private val TAG = "UpdateReminder"
    private val UPDATE_PERIOD = 24

    companion object {
        const val UPDATE_TIME = "update_time"
        const val UPDATE_TIMEZONE = "update_timezone"
    }

    fun saveUpdateReminder(context: Context) {
        Log.i(TAG, "saveUpdateReminder()")
        val format = SimpleDateFormat("MM/dd/yyyy hh:mm aa zzz", Locale.ENGLISH)


        val calendar = Calendar.getInstance()
        Log.i(
                TAG, "saveUpdateReminder(), TimeZone:" + calendar.timeZone.id
                + ", M:" + format.format(calendar.time)
        )

        val pref = SharedPref.getInstance(context)
        pref.setValue(UPDATE_TIME, calendar.timeInMillis)
        pref.setValue(UPDATE_TIMEZONE, calendar.timeZone.id)
        pref.commit()
    }

    fun isSuggesttedToUpdateInTime(context: Context): Boolean {
        Log.i(TAG, "isSuggesttedToUpdateInTime()")
        val now = Calendar.getInstance()

        val pref = SharedPref.getInstance(context)
        val updateTime =
                pref.getValue(UPDATE_TIME, java.lang.Long.valueOf(0))
        val timezoneID =
                pref.getValue(UPDATE_TIMEZONE, now.timeZone.id)

        if (updateTime == 0L) {
            return false
        }

        val lastUpdateTime = Calendar.getInstance()
        lastUpdateTime.timeInMillis = updateTime
        lastUpdateTime.timeZone = TimeZone.getTimeZone(timezoneID)

        val seconds = (now.timeInMillis - lastUpdateTime.timeInMillis) / 1000
        val hours = (seconds / 3600).toInt()

        Log.i(TAG, "seconds:$seconds, 相隔的時數=$hours")
        return hours < UPDATE_PERIOD
    }
}