package com.example.foodpanda_capstone.`interface`

import com.example.foodpanda_capstone.model.LoginResult

interface UserRepository {
    suspend fun login(username: String, password: String): Boolean
}
