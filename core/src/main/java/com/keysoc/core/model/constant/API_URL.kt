package com.keysoc.core.model.constant

object API_URL {


    object ForgetPassword {
        // RequestResetPwCode -> VerifyResetPwCode -> SetPassword
//        const val FORGET_PASSWORD = "$POSTFIX/ResetPassword"

        const val RequestResetPwCode = "/mobile/RequestResetPwCode"
        const val VerifyResetPwCode = "/mobile/VerifyResetPwCode"
    }

    object Register {
//        const val CHECK_ACCOUNT_EXIST = "$POSTFIX/CheckIfAccountExist"

        const val CheckRegistrationPassCode = "/mobile/CheckRegistrationPasscode"
        const val RequestSMS = "/mobile/RequestSMSCode"
        const val VerifyRegCode = "/mobile/VerifyRegCode"

        const val RedeemRegistrationPassCode = "/mobile/RedeemRegistrationPasscode"
        const val SetPassword = "/mobile/SetPassword"
    }

    object Main{
        const val Login = "/mobile/Login"
        const val Logout = "/mobile/Logout"
        const val RequestToken = "/mobile/RequestToken"
        const val RefreshToken = "/mobile/RefreshToken"

        const val GetLandingInfo = "/mobile/GetLandingInfo"
    }

    object Property {
//        const val ADD_PROPERTY = "$POSTFIX/RegisterNewUnit"
//        const val REMOVE_PROPERTY = "$POSTFIX/RemoveProperty"
        const val GetbuildingDescription = "/mobile/GetBuildingDescription"

        const val GetNearByInfo = "/mobile/GetNearByInfo"
        const val GetTransportInfo = "/mobile/GetTransportInfo"
        const val URL_FEEDBACK = "/mobile/Feedback"

    }

    object Visitor_Go {
        const val GetVisitorThemeList = "/visitorgo/GetVisitorThemeList"
//        const val GET_QR_THEME_STBARTHS = "MobileStBarths/GetVisitorThemeList"
        const val VisitorRegistration = "/visitorgo/VisitorRegistration"
        const val GetVisitorList = "/visitorgo/GetVisitorList"
        const val DeleteVisitorCode = "/visitorgo/DeleteVisitorCode"
    }

    object Mobile_Pass {
        const val API_HID_SUPPORT_URL_CHI = "AccessControl/SupportedDevicesChi"
        const val API_HID_SUPPORT_URL_SC_CHI = "AccessControl/SupportedDevicesSChi"
        const val API_HID_SUPPORT_URL_EN = "AccessControl/SupportedDevicesEn"

        const val CheckQRProfile = "/access/CheckQRProfile"

        const val ApplyQRMobileKey = "/access/ApplyQRMobileKey"

//        const val UPDATE_MOBILE_DETAILS = "AccessControl/updateMobileDetails"
        const val GetQRMobileKey = "/access/GetQRMobileKey"
        const val CheckQRMobileKey = "/access/CheckQRMobileKey"
        const val LOGIN_ERROR_LOG = "/Mobile/LoginErrorLog"
        const val Check_QR_Pre_Reg = "/access/CheckQRPreReg"

    }


    object Payment {
        const val StripePay = "Payment/StripePay"
        const val RSPCODE = "rspcode"
        const val ORDERNO = "ordernum"
        const val EXTRA_DATA = "EXTRA_DATA"
        const val WeChatPay = "Payment/Pay_WeChat"
    }

    object InboxMsg{
        const val GetInboxMsgList = "/mobile/GetInboxMsgList"
        const val SetInboxMsgStatus = "/mobile/SetInboxMsgStatus"
//        const val ReplyMessage = "/mobile/ReplyMessage"
    }


}