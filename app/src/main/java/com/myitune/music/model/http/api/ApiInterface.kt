package com.myitune.music.model.http.api

import com.myitune.core.model.constant.API_URL
import com.myitune.music.model.http.respond.SearchRespond
import retrofit2.Call
import retrofit2.http.*
import java.util.HashMap

interface ApiInterface {


    /* Register */

//    @POST(API_URL.Register.CheckRegistrationPassCode)
//    fun checkRegistrationPassCode(@Body request: HashMap<String, String>): Call<DataRespond<CheckRegPassCodeRespond>>


    @GET(API_URL.Album.GetSearchRequest)
    fun getSearchRequest(
        @Query("term") term: String,
        @Query("entity") entity: String,
    ): Call<SearchRespond?>

}