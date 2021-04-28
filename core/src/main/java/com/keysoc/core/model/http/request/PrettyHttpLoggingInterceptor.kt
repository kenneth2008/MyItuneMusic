package com.keysoc.core.model.http.request

import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import okhttp3.logging.HttpLoggingInterceptor
import com.keysoc.core.BuildConfig


object PrettyHttpLoggingInterceptor {

    fun getHttpLoggingInterceptor(): HttpLoggingInterceptor{
        return HttpLoggingInterceptor { message ->

            if (message.startsWith("{") || message.startsWith("[")) {
                try {
                    val gson = GsonBuilder().setPrettyPrinting()
                        .create()
                    val prettyPrintJson = gson.toJson(JsonParser.parseString(message))

                    if (BuildConfig.DEBUG) {
                        Log.i(javaClass.simpleName,
                            "=======================This is the NetworkResponse json data======================")
                        val maxLogSize = 1000
                        for (i in 0..prettyPrintJson.length / maxLogSize) {
                            val start = i * maxLogSize
                            var end = (i + 1) * maxLogSize
                            end = if (end > prettyPrintJson.length) prettyPrintJson.length else end
                            Log.i(javaClass.simpleName, prettyPrintJson.substring(start, end))
                        }
                    }
                } catch (m: JsonSyntaxException) {
                    Log.i(javaClass.simpleName, message)
                }
            } else {
                Log.i(javaClass.simpleName, message)
            }
        }
    }

}