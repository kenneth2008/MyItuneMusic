package com.keysoc.core.model.http.request

import android.util.Log
import com.keysoc.core.model.http.SimpleRequestListener
import com.keysoc.core.model.http.response.BaseRespond
import com.keysoc.core.view.activity.base.BaseActivity
import retrofit2.Call
import retrofit2.Response

class LogoutRequest(
    private val act: BaseActivity<*>,
    private val requestListener: SimpleRequestListener<BaseRespond?>,
) : BaseCoreNetWorkRequest() {

    fun execute() {
        Log.i(javaClass.simpleName, "execute()")

        val params = HashMap<String, Any>()

        params["deviceId"] = act.myApp.configManager.DeviceId ?: ""


        coreApi.logout(
            params
        ).enqueue(object : retrofit2.Callback<BaseRespond?> {
            override fun onResponse(call: Call<BaseRespond?>, respond: Response<BaseRespond?>?) {
                Log.i(javaClass.simpleName, "onResponse()")

                val jsonResponse = respond?.body()
                /* Success Result */
                if (respond?.isSuccessful == true && jsonResponse?.isSuccess == true) {

                    act.myApp.configManager.login(act)

                    requestListener.onSuccess(jsonResponse)

                } else {
                    /* Respond status no success result */
                    requestListener.onError(jsonResponse)
                }
            }

            override fun onFailure(call: Call<BaseRespond?>, t: Throwable) {
                Log.e(javaClass.simpleName, "onErrorResponse(), t.message: ${t.message}")
                requestListener.onError(null)
            }

        })

    }

}