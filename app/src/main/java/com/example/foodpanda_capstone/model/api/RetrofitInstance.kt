package com.example.foodpanda_capstone.model.api

import com.example.foodpanda_capstone.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitClient {
    private const val BASE_URL = "http://192.168.0.165:3000/" // Home
//    private const val BASE_URL = "http://10.80.176.107:3000/" // Office
//    private const val BASE_URL = "https://panda-food-playlist-backend.onrender.com/" // Live Server

    private val okHttp3Client = OkHttpClient.Builder()

    init {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        if(BuildConfig.DEBUG){
            okHttp3Client.addInterceptor(logging)
        }
    }

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttp3Client.build())
            .build()
    }
}

object PlaylistApiClient {
    val apiService: PlaylistApiService by lazy {
        RetrofitClient.retrofit.create(PlaylistApiService::class.java)
    }
}
