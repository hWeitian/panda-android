package com.example.foodpanda_capstone.model.api

import com.example.foodpanda_capstone.model.LoginForm
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface LoginFormApiService {
    @POST("users/create")
    suspend fun createUser(@Body loginForm: LoginForm): LoginForm

    @GET("users/{userId}")
    suspend fun getUser(@Path("userid") userId: String): LoginForm




}
