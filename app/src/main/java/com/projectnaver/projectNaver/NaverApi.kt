package com.projectnaver.projectNaver

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query
//Naver Api Qery
interface NaverApi {
    //modle
    @GET("search/{type}")
    fun getDate(
        @Header("X-Naver-Client-Id") clientId : String,
        @Header("X-Naver-Client-Secret") clientPw :String,
        @Path("type") type : String,
        @Query("query") query : String
    ): Call<Movie>

}
