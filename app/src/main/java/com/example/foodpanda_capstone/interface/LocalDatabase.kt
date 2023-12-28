package com.example.foodpanda_capstone.`interface`

import com.example.foodpanda_capstone.model.LoginForm


interface LocalDatabase {
    suspend fun saveUserLoginData(userLoginData: LoginForm )
}
