package com.example.foodpanda_capstone.`interface`

import com.example.foodpanda_capstone.model.LoginForm

interface NetworkService {
    suspend fun validatorLoginCredentials(email: String, password: String) : Boolean
//    suspend fun fetchUserLoginData(email: String): LoginForm
}
