package com.example.foodpanda_capstone.`interface`

interface NetworkService {
    suspend fun validatorLoginCredentials(email: String, password: String) : Boolean
//    suspend fun fetchUserLoginData(email: String): LoginForm
}
