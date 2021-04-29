package com.myitune.music.model.http.request.base

import com.myitune.core.model.constant.IConstants
import com.myitune.core.model.http.request.PrettyHttpLoggingInterceptor
import com.myitune.music.BuildConfig
import com.myitune.music.model.http.api.ApiInterface
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


open class BaseNetWorkRequest {

    private val baseUrl = IConstants.DomainName.getApiDomain(BuildConfig.IS_UAT)
    protected open val timeoutInSeconds: Long = 30000L

    private val retrofit: Retrofit

    protected val api: ApiInterface

    init {
//        Log.i(javaClass.simpleName, "init(), baseUrl: $baseUrl")

        /* Interceptor */
        val httpLoggingInterceptor = PrettyHttpLoggingInterceptor.getHttpLoggingInterceptor()
            .apply { level = HttpLoggingInterceptor.Level.BODY }

        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient().newBuilder()
                    .connectTimeout(timeoutInSeconds, TimeUnit.MILLISECONDS) // connect timeout
                    .readTimeout(timeoutInSeconds, TimeUnit.MILLISECONDS) // socket timeout
                    .addInterceptor(httpLoggingInterceptor)
                    .addInterceptor(Interceptor { chain ->
                        val original: Request = chain.request()

                        val request: Request = original.newBuilder()
                            .header("Content-Type", "application/json")
                            .method(original.method, original.body)
                            .build()

                        chain.proceed(request)
                    })
                    .build()
            )
            .build()

        api = retrofit.create(ApiInterface::class.java)
    }


}