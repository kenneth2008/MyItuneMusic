package com.myitune.core.model.config.language


import android.content.Context
import android.os.Build
import android.os.LocaleList
import android.util.Log
import com.myitune.core.model.constant.IConstants.SHARED_PREFERENCE.SHARED_PREFERENCE_LANGUAGE
import com.myitune.datamodel.constant.LANG.LANG_CN
import com.myitune.datamodel.constant.LANG.LANG_EN
import com.myitune.datamodel.constant.LANG.LANG_ZH
import com.myitune.datamodel.sharedpref.SharedPref
import java.util.*

/**
 * Manage any conversion of the application language
 * Respond to save and setup language
 */

class LanguageManager(private val myApp: Context) {

    companion object {
        private const val TAG = "LanguageManager"
    }

    var lang: String? = null
        private set


    init {
        getLastLanguageFromDB()
        setLangToTranslator()
    }

//    fun getCountryCodePickerLang(): CountryCodePicker.Language {
//        return when (lang) {
//            LANG_EN -> CountryCodePicker.Language.ENGLISH
//            LANG_CN -> CountryCodePicker.Language.CHINESE_SIMPLIFIED
//            else -> CountryCodePicker.Language.CHINESE_TRADITIONAL
//        }
//    }


//    fun getLangForCustomerService(): String {
//        return when (lang) {
//            LANG_ZH -> "1"
//            LANG_EN -> "0"
//            LANG_CN -> "zh-CN"
//            else -> "1"
//        }
//    }

    fun getLangForLanding(): Int {

        return when (lang) {
            LANG_ZH -> 1
            LANG_EN -> 2
            LANG_CN -> 3
            else -> 1
        }
    }

    fun getLocaleByApplicationLang(): Locale {

        return when (lang) {
            LANG_EN -> Locale(LANG_EN)
            LANG_ZH -> Locale(LANG_ZH, "HK")
            LANG_CN -> Locale("zh", "CN")
            else -> Locale(LANG_ZH, "HK")
        }
    }


    fun getLocaleForDateFormatter(): Locale {
        return when (lang) {
            LANG_EN -> Locale.ENGLISH
            LANG_ZH -> Locale.TRADITIONAL_CHINESE
            LANG_CN -> Locale.SIMPLIFIED_CHINESE
            else -> Locale.TRADITIONAL_CHINESE
        }
    }

    fun getLangForPayment(): String {
        return when (lang) {
            LANG_EN -> "en_US"
            LANG_ZH -> "zh_TW"
            LANG_CN -> "zh_CN"
            else -> "zh_TW"
        }
    }


    fun hasSetLang(): Boolean {
        /* if default value is not null, first load on language page will skipped */
        val sharedPref = SharedPref.getInstance(myApp)
        val language = sharedPref.getValue(SHARED_PREFERENCE_LANGUAGE, "")
        Log.i(TAG, "hasSetLang(), $language")
        return !language.isNullOrEmpty()
    }

    /**
     * Get Last Language From DataBase
     */
    private fun getLastLanguageFromDB() {
        if (lang.isNullOrEmpty()) {
            /* if default value is not null, first load on language page will skipped */
            lang = SharedPref.getInstance(myApp).getValue(SHARED_PREFERENCE_LANGUAGE, "")
        }
    }

    /**
     * If there are third party sdk use language setting, update it here
     * e.g. Smart Home
     */
    fun setLang(lang: String) {
        this.lang = lang
        Log.i(TAG, "setLang(), lang:$lang")

        val sharedPref = SharedPref.getInstance(myApp)
        sharedPref.setValue(SHARED_PREFERENCE_LANGUAGE, lang)
        sharedPref.commit()


//        if (lang.isNotEmpty()) setLangToContext(context)


        setLangToTranslator()

    }

    /**
     * This must run before Activity.setContentLayout
     */
    fun setLanguageToAllContext(context: Context) {

        // context
        setLangToContext(context)
        setLangToContext(context.applicationContext)

        setLangToTranslator()
    }

    fun setLangToContext(context: Context): Context? {
        Log.i(TAG, "setLangToActivity(), lang:$lang")

        val newLocale = getLocaleByApplicationLang()

        /* Acton 1 */
        Locale.setDefault(newLocale)

        val res = context.resources
        val configuration = res.configuration

        /* Acton 2 */
        configuration.setLocale(newLocale)

        val newContext: Context

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            val localeList: LocaleList?
            localeList = LocaleList(newLocale)
            /* Acton 3.1 */
            LocaleList.setDefault(localeList)
            /* Acton 3.2 */
            configuration.setLocales(localeList)
            /* Acton 3.3 */
            newContext = context.createConfigurationContext(configuration)
        } else {
            /* Acton 3.3 */
            newContext = context.createConfigurationContext(configuration)
        }

        /* Acton 4 */
//        newContext.resources.updateConfiguration(newContext.resources.configuration, res.displayMetrics)
        res.updateConfiguration(configuration, res.displayMetrics)

        return newContext
    }

    private fun setLangToTranslator() {
        // Log.i(TAG, "setLangToTranslator(), lang:$lang")
        // Translator language
        if (lang != null) {
            val sLangSign: String = when {
                lang.equals(LANG_EN, ignoreCase = true) -> "Eng"

                lang.equals(LANG_CN, ignoreCase = true) -> "SChi"

                else -> "Chi"

            }

//            Translator.updateLanguage(sLangSign)
            // Log.i(TAG, "setLangToTranslator(), Translator.prefix:" + Translator.prefix)
        }
    }

}
