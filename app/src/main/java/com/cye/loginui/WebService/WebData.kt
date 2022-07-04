package com.cye.loginui.WebService

import com.cye.loginui.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun getRetrofit(): Retrofit {
    val httpClient = OkHttpClient.Builder()
        .readTimeout(90, TimeUnit.SECONDS)
        .writeTimeout(90, TimeUnit.SECONDS)
        .connectTimeout(90, TimeUnit.SECONDS)

    httpClient.addInterceptor(printApiReqResponse())
    httpClient.addInterceptor(Interceptor { chain ->

        val original = chain.request()

        val request = original.newBuilder()
            .header("Accept", "application/json")
            .header("Authorization", "auth-token")
//                .addHeader("Authorization", "Bearer " + getToken())
            .method(original.method, original.body)
            .build()

        chain.proceed(request)
    })

    val okHttpClient = httpClient.build()
    return Retrofit.Builder()
        .baseUrl(getBaseUrl())
        .addConverterFactory(NullOnEmptyConverterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
}

fun getBaseUrl(): String {
    return BuildConfig.BASE_URL
}

//printApiReqResponse on debug mode
fun printApiReqResponse(): HttpLoggingInterceptor {
    val interceptor = HttpLoggingInterceptor { message: String? ->
        if (BuildConfig.DEBUG)
            println(message)
    }
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    return interceptor
}

fun getApi(): ApiService = getRetrofit().create(ApiService::class.java)