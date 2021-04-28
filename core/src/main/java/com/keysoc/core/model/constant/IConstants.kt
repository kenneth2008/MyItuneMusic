package com.keysoc.core.model.constant

import java.util.concurrent.TimeUnit

object IConstants {

    object DomainName {


        fun getApiDomain(isUAT: Boolean): String {
            val API_DOMAIN_UAT = "https://whizbizapi.necess.com.hk" // uat
            val API_DOMAIN_PRO = "https://sopropapi.hongyip.com/" // Production
//            return API_DOMAIN_UAT
            return if (isUAT) {
                API_DOMAIN_UAT
            } else {
                API_DOMAIN_PRO
            }
        }

    }

    object SHARED_PREFERENCE {
        const val SHARED_PREFERENCE_LANGUAGE = "SHARED_PREFERENCE_LANGUAGE"

        //        const val HAS_READ_HOW_IT_WORKS = "has_read_how_it_works"
//        const val HAS_ASK_LOCATION_PERMISSION = "has_ask_location_permission"

        const val CONFIG_MANAGER = "CONFIG_MANAGER"
        const val MOBILE_PASS_CONFIG_MANAGER = "MOBILE_PASS_CONFIG_MANAGER"

        const val LANG = "lang"
    }

    object BASIC {
        const val DOUBLE_BACK_PRESSED_DURATION: Long = 2000 // 2 seconds
        val LANDING_UPDATE_DURATION: Long = TimeUnit.MINUTES.toMillis(3)

        const val BASIC_REQUEST_TIMEOUT = 30000
//        const val CHAT_BOT_REQUEST_TIMEOUT = 60000
//        const val NFC_INTERVAL = 2000

        const val expression: String =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        const val PASSWORD_MIN_LENGTH = 8
        //        const val PHONE_MIN_LENGTH = 8
        const val PASSCODE_LENGTH = 6

        const val BUILDING_ID = "32d30b35-2698-44c2-9e9d-b26ecb78e080"
        const val EXTRA_RESET_PASSCODE = "EXTRA_RESET_PASSCODE"
    }

    object MobilePass {
        const val SCREEN_LOCK = "mobilePassScreenLock"
        const val LAST_TAP_DURATION: Long = 2000L
        const val HAS_INVITED = "hasInvited"

        const val NTP_TIME_DIFFERENCE_ALLOWANCE = 60000L // 1 min
    }

}