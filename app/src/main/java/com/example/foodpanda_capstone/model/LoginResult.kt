package com.example.foodpanda_capstone.model

sealed class LoginResult {
    object Loading : LoginResult()
//    data class Success(val userLoginData: LoginForm) : LoginResult()
    data class Success(val userLoginData: String) : LoginResult()

    data class Error(val errorMessage: String) : LoginResult()
}
