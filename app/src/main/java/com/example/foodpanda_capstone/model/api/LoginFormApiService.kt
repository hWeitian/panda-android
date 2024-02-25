package com.example.foodpanda_capstone.model.api

import com.example.foodpanda_capstone.model.LoginForm
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginFormApiService {
    @POST("users/create")
    suspend fun createUser(@Body loginForm: LoginForm): LoginForm

}
