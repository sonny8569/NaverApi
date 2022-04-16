package com.projectnaver.projectNaver

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.util.concurrent.TimeUnit


class Client {
    companion object {
        private const val TAG = "Client"
    }
    private val Url: String ="https://openapi.naver.com/v1/"
    private val CONNECT_TIMEOUT:Long = 15
    //log용도
    private val okHttpClient : OkHttpClient
        get()
        {   val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            return OkHttpClient().newBuilder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .build()
        }

    //retrofit
    val movieRetrofit: NaverApi
        get() {
            Log.d(TAG, "Retrofit is Start")
            return Retrofit.Builder().baseUrl(Url)//movieUri
                .client(okHttpClient)//Log 출력 용도
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//RxJava2를 사용하도록 Factory생성
                .addConverterFactory(GsonConverterFactory.create())//Gson을 쓸 수 있도록 Factory생성
                .build().create(NaverApi::class.java)//MovieService Interface사용
        }

}

