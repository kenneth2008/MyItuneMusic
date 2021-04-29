package com.myitune.core.model.http.response

import com.myitune.core.model.config.MyApp


/**
 * Created by KennethTse on 9/2/2018.
 */

open class BaseRespond : EmptyResponse() {

    open var status: Int? = 0

    open var error: String? = null

    open fun getErrMsg(myApp: MyApp): String? {
        return error
//        return when (myApp.languageManager.lang) {
//            LANG.LANG_EN -> errMsg_Eng
//            LANG.LANG_ZH -> errMsg_Chi
//            else -> errMsg_Chi
//        }
    }


    open val isSuccess: Boolean
        get() = status ?: 0 > 0


}