package com.example.foodpanda_capstone.model
import android.util.Log
import com.example.foodpanda_capstone.`interface`.NetworkService

class NetworkServiceImpl : NetworkService {
    override suspend fun validatorLoginCredentials(email: String, password: String): Boolean {
        return email == "user123@gmail.com" && password == "1234567890"
    }

//    override suspend fun fetchUserLoginData(email: String): LoginForm {
//        return LoginForm(email = "user123@gmail.com", password = "1234567890")
//    }
}
