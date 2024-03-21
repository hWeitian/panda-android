package com.example.foodpanda_capstone.model.api

import com.example.foodpanda_capstone.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitClient {
    private const val BASE_URL = BaseUrl.BASE_URL
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

object BaseUrl {
//    const val BASE_URL = "http://192.168.0.166:3000/" // Home
    const val BASE_URL = "http://192.168.0.166:8080/" // Final Home
//     const val BASE_URL = "http://10.224.32.210:3000/" // Home
//     const val BASE_URL = "http://10.80.176.107:3000/" // Office
//    const val BASE_URL = "https://panda-food-playlist-backend.onrender.com/" // Live Server
//    const val BASE_URL = "https://playlist.1.sg-1.fl0.io/"
}

object PlaylistApiClient {
    val apiService: PlaylistApiService by lazy {
        RetrofitClient.retrofit.create(PlaylistApiService::class.java)
    }
}

object LoginFormApiClient {
    val apiService: LoginFormApiService by lazy {
        RetrofitClient.retrofit.create(LoginFormApiService::class.java)
    }
}
