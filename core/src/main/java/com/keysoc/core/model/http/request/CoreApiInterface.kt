package com.keysoc.core.model.http.request

import com.keysoc.core.model.constant.API_URL
import com.keysoc.core.model.http.response.BaseRespond
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.*

interface CoreApiInterface {

    @POST(API_URL.Main.Logout)
    fun logout(@Body request: HashMap<String, Any>): Call<BaseRespond?>

}