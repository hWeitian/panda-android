package com.example.foodpanda_capstone.model

import com.example.foodpanda_capstone.`interface`.NetworkService
import com.example.foodpanda_capstone.`interface`.UserRepository

class UserRepositoryImpl(private val networkService: NetworkService) : UserRepository {

    override suspend fun login(username: String, password: String): Boolean {
        return networkService.validatorLoginCredentials(username, password)
    }

}
