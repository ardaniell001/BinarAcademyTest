package com.binar.ardanil.configuration.network

import com.binar.ardanil.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class ApiManager {

    private val BASE_URL = BuildConfig.API_URL

    private var retrofit: Retrofit? = null
    private var services: ApiEndpoint? = null
    private val REQUEST_TIMEOUT = 30

    fun getService(): ApiEndpoint? {
        if(services != null){
            return services
        }
        if(retrofit == null){
            initializeRetrofit()
        }
        services = retrofit?.create(ApiEndpoint::class.java)
        return services
    }

    private fun initializeRetrofit(){
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val httpClient = OkHttpClient().newBuilder()
        httpClient.readTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
        httpClient.connectTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
        httpClient.addInterceptor(logging)

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient.build())
            .addConverterFactory(MoshiConverterFactory.create().withNullSerialization())
            .build()
    }
}