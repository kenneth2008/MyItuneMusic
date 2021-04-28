package com.keysoc.core.model.config

import android.content.Context
import android.util.Log
import com.keysoc.core.model.constant.IConstants.SHARED_PREFERENCE.CONFIG_MANAGER
import com.keysoc.datamodel.sharedpref.SharedPref

/**
 * Created by KennethTse on 10/11/2017.
 */

class ConfigManager private constructor() {

    var DeviceId: String? = null

    var regId: String? = null
    var applink: String? = null
    var lastver: Float? = null

    /* notification */
    var isVIBRATION: Boolean = false
    var isSOUND: Boolean = false
    var isSHOW_NOTIFICATION: Boolean = true


    var isForceUpdate: Boolean = false
    var lastTimeUnitID: Long? = null

    var isLoggedIn: Boolean = false

    // Is single sign on
    var isSSO: Boolean = false

    /* Access Token */
    var access_token: String? = null
    var expires_in: Int? = null
    var refresh_token: String? = null

    fun save(context: Context) {
        val sharedPref = SharedPref.getInstance(context)
        sharedPref.setObjectValue(CONFIG_MANAGER, this)
        sharedPref.commit()
    }

    fun reset(context: Context) {
        //setDEVICE_ID("");
        DeviceId = ""
        regId = ""
        lastTimeUnitID = null
        isLoggedIn = false

        access_token = null
        expires_in = null
        refresh_token = null
        save(context)
    }


    fun login(context: Context) {
        Log.i(TAG, "login(), isLoggedIn:$isLoggedIn")
        isLoggedIn = true
        save(context)
    }

    fun logout(context: Context) {
        Log.i(TAG, "logout(), isLoggedIn:$isLoggedIn")
        DeviceId = ""
        regId = ""
        lastTimeUnitID = null
        isLoggedIn = false

        access_token = null
        expires_in = null
        refresh_token = null
        save(context)
    }

    companion object {

        const val TAG = "ConfigManager"

        /**
         * Method to create ConfigManager with default setting
         *
         * @return
         */
        private val defaultConfigManager: ConfigManager
            get() {
                Log.i(TAG, "ConfigManager.getDefaultConfigManager()")
                return ConfigManager()
            }

        fun getInstance(act: Context): ConfigManager {
            val mPref = SharedPref.getInstance(act)
            var configManager = mPref.getObjectValue(CONFIG_MANAGER, ConfigManager::class.java)
            Log.i(TAG, "configManager == null? " + (configManager == null))
            if (configManager == null) {
                configManager = defaultConfigManager
            }

            return configManager
        }
    }
}