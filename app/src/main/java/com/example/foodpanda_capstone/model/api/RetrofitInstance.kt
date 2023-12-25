package com.example.foodpanda_capstone.model.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    private var retrofit: Retrofit? = null
    private val BASE_URL: String = "http://192.168.0.166:3000/"

    fun getService(): PlaylistApiService {
        if(retrofit == null){
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                // Tell retrofit to use GsonConverterFactory to serialise and deserialise JSON data.
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!.create(PlaylistApiService::class.java)
    }
}
