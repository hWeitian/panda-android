package com.example.foodpanda_capstone.model
import android.util.Log
import androidx.compose.ui.text.toLowerCase
import com.example.foodpanda_capstone.`interface`.NetworkService
import com.example.foodpanda_capstone.model.mock_data.LoginFormData

class NetworkServiceImpl : NetworkService {
    override suspend fun validatorLoginCredentials(email: String, password: String): Boolean {
//        val lowercaseEmail = email.lowercase()
//        val loginIsCorrect = LoginFormData.any { it.email.lowercase() == lowercaseEmail && it.password == password }
//        // Log the value
//        Log.d("LoginValidation", "Login is correct: $loginIsCorrect")
//        return loginIsCorrect
        return LoginFormData.any { it.email == email && it.password == password }
    }


//    override suspend fun fetchUserLoginData(email: String): LoginForm {
//        return LoginForm(email = "user123@gmail.com", password = "1234567890")
//    }
}
