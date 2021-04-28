package com.keysoc.core.model.http.request

import com.keysoc.core.BuildConfig
import com.keysoc.core.model.constant.IConstants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


open class BaseCoreNetWorkRequest {

    private val baseUrl = IConstants.DomainName.getApiDomain(BuildConfig.IS_UAT)
    protected open val timeoutInSeconds: Long = 30000L

    private val retrofit: Retrofit

    protected val coreApi: CoreApiInterface

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
                            .header("ApiKey", "24852485-2485-2485-2485-248524852485")
                            .method(original.method, original.body)
                            .build()

                        chain.proceed(request)
                    })
                    .build()
            )
//            .addConverterFactory(EnumConverterFactory())
            .build()

        coreApi = retrofit.create(CoreApiInterface::class.java)
    }


}