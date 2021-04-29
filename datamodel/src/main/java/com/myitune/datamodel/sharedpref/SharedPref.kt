package com.myitune.datamodel.sharedpref

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.myitune.datamodel.BuildConfig
import com.myitune.datamodel.R
import org.json.JSONArray
import org.json.JSONException
import java.lang.reflect.Type


/**
 * Created by ChingLeung on 15/11/2017.
 */

@SuppressLint("CommitPrefEdits")
class SharedPref(private val mContext: Context) {

    private val dbName = if(BuildConfig.IS_UAT) {
        mContext.getString(R.string.shared_database_name) + mContext.getString(R.string.db_name_postfix)
    }else{
        mContext.getString(R.string.shared_database_name)
    }

    private var gson: Gson? = null
    private var mTransaction = false
    private val preferences: SharedPreferences =
        mContext.getSharedPreferences(dbName, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = preferences.edit()

    fun getGson(): Gson {
        if (null == gson) {
            gson = Gson()
        }
        return gson as Gson
    }

    fun beginTransaction() {
        mTransaction = true
    }

    fun commit() {
        editor.apply()
        mTransaction = false
    }

    fun setValue(key: String, value: String) {
        editor.putString(key, value)
        if (!mTransaction) {
            editor.apply()
        }
    }

    fun getValue(key: String, defaultValue: String): String? {
        return preferences.getString(key, defaultValue)
    }

    fun setValue(key: String, value: Int) {
        editor.putInt(key, value)
        if (!mTransaction) {
            editor.apply()
        }
    }

    fun getValue(key: String, defaultValue: Int): Int {
        val preferences = mContext.getSharedPreferences(dbName, Context.MODE_PRIVATE)
        return preferences.getInt(key, defaultValue)
    }

    fun setValue(key: String, value: Long) {
        editor.putLong(key, value)
        if (!mTransaction) {
            editor.apply()
        }
    }

    fun getValue(key: String, defaultValue: Long): Long {
        return preferences.getLong(key, defaultValue)
    }

    fun setValue(key: String, value: Float) {
        editor.putFloat(key, value)
        if (!mTransaction) {
            editor.apply()
        }
    }

    fun getValue(key: String, defaultValue: Float): Float {
        return preferences.getFloat(key, defaultValue)
    }

    fun setValue(key: String, value: Boolean?) {
        editor.putBoolean(key, value!!)
        if (!mTransaction) {
            editor.apply()
        }
    }

    fun getValue(key: String, defaultValue: Boolean): Boolean {
        return preferences.getBoolean(key, defaultValue)
    }

    fun  getStringListValue(key: String, desc: Boolean?): ArrayList<String> {
        var itemList = ArrayList<String>()
        val json = preferences.getString(key, null)
        if (!json.isNullOrEmpty()) {
            Log.i(TAG, "preferences.getString:$json")

            try {

                val groupListType: Type =
                    object : TypeToken<ArrayList<String>?>() {}.type
                itemList = getGson().fromJson(json, groupListType)

                if (desc!!) {
                    itemList.reverse()
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }

        }
        return itemList
    }


    fun <T> setListValue(key: String, value: List<T>) {
        editor.putString(key, JSONArray(value).toString())
        if (!mTransaction) {
            editor.apply()
        }
    }

    fun setObjectValue(key: String, `object`: Any): Boolean {

        val jsonString: String = getGson().toJson(`object`)

        Log.i(TAG, jsonString)

        editor.putString(key, jsonString)
        editor.apply()

        return true
    }

    fun removeValue(key: String) {
        editor.remove(key)
        editor.apply()
    }

    fun <T> getObjectValue(key: String, c: Class<T>): T? {

        val jsonString = preferences.getString(key, null)
        //Log.i(TAG, StringUtils.isNotNullOrEmpty(jsonString) ? jsonString : "message null");
        val `object`: Any
        try {
            `object` = getGson().fromJson(jsonString, c)!!
        } catch (e: NullPointerException) {
            return null
        }

        return c.cast(`object`)
    }

    /**
     * A bug in android 7.0, when using HashMap<T, U> as method return type
     * TypeToken<HashMap<String, String>>() {}.type
     * cannot get T, U as String, String, to prevent this, don't use dynamic type for TypeToken
     */
    fun getStringMapValue(key: String): HashMap<String, String>? {

        val jsonString = preferences.getString(key, null)
        //Log.i(TAG, StringUtils.isNotNullOrEmpty(jsonString) ? jsonString : "message null");
        var `object`: HashMap<String, String>? = null
        try {
            val type: Type = object : TypeToken<HashMap<String, String>>() {}.type
            `object` = getGson().fromJson(jsonString, type)!!
        } catch (e: Exception) {
        }

        return `object`
    }


    companion object {
        private const val TAG = "SharedPref"

        fun getInstance(context: Context): SharedPref {
            return SharedPref(context)
        }
    }

}
