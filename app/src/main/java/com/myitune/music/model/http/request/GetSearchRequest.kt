package com.myitune.music.model.http.request

import com.myitune.core.model.http.SimpleRequestListener
import com.myitune.core.view.activity.base.BaseActivity
import com.myitune.datamodel.database.AppDatabase
import com.myitune.music.model.http.request.base.BaseNetWorkRequest
import com.myitune.music.model.http.respond.SearchRespond
import retrofit2.Call
import retrofit2.Response

class GetSearchRequest(

    private val act: BaseActivity<*>,
    private val listener: SimpleRequestListener<SearchRespond>,

    ) : BaseNetWorkRequest() {


    fun execute() {

//        val params = HashMap<String, Any>()

//        params["term"] = "jack+johnson"
//        params["entity"] = "album"


        api.getSearchRequest(
            "jack+johnson", "album"
        ).enqueue(object : retrofit2.Callback<SearchRespond?> {

            override fun onFailure(
                call: Call<SearchRespond?>,
                t: Throwable,
            ) {
                listener.onError(null)
            }

            override fun onResponse(
                call: Call<SearchRespond?>,
                response: Response<SearchRespond?>?,
            ) {
                val jsonResponse = response?.body()
                if (response?.isSuccessful == true) {

                    val dao = AppDatabase.getDatabase(act.myApp).getAlbumDataDao()

                    dao.deleteAndInsertAll(jsonResponse?.results?:ArrayList())

                    listener.onSuccess(jsonResponse)
                } else {
                    listener.onError(jsonResponse)
                }
            }
        })
    }

}