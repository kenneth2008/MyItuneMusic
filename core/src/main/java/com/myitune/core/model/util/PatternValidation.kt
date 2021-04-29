package com.hongyip.likon.core.model.util

import com.myitune.core.model.constant.IConstants
import java.util.regex.Pattern

object PatternValidation {

    fun isEmailValid(email: String): Boolean {
        val inputStr = email.trim { it <= ' ' }
        val pattern = Pattern.compile(IConstants.BASIC.expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(inputStr)
        return matcher.matches()
    }

}