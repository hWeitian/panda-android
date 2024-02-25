package com.example.foodpanda_capstone.model

//import LocalDatabase
//import NetworkService
//import androidx.compose.material3.Text
import com.example.foodpanda_capstone.`interface`.NetworkService
import com.example.foodpanda_capstone.`interface`.UserRepository

class LoginFormRepository(private val networkService: NetworkService) : UserRepository {

//    suspend fun login(email: String, password: String): LoginResult {
//        try {
//            // Validate credentials through network service
//            val isValidCredentials = networkService.validatorLoginCredentials(email, password)
//
//            if (isValidCredentials) {
//                // Fetch user data from the network
//                val userLoginData = networkService.fetchUserLoginData(email)
//
//                // Save user data to the local database
////                localDatabase.saveUserLoginData(userLoginData)
//                // Return success state
//                return LoginResult.Success(userLoginData)
//            } else {
//                // Invalid credentials
//                return LoginResult.Error("Invalid credentials")
//            }
//        } catch (e: Exception) {
//            // Handle network errors
//            return LoginResult.Error("Network error")
//        }
//    }

//    override suspend fun validatorLoginCredentials(email: String, password: String): Boolean {
//
//        try {
//            // Validate credentials through network service
//            val isValidCredentials = networkService.validatorLoginCredentials(email, password)
//
//            if (isValidCredentials) {
//                println("Login Successful!")
//                return true
//            } else {
//                println("Invalid Credentials")
//                return false
//            }
//        }
//            catch (e: Exception) {
//                // Handle network errors
//                println("Network Error")
//                return false
//            }
//    }

    override suspend fun login(email: String, password: String): Boolean {

        try {
            // Validate credentials through network service
            val isValidCredentials = networkService.validatorLoginCredentials(email, password)

            if (isValidCredentials) {
                println("Login Successful")
                return true

            } else {
                println("Login Failed, Please Try again.")
                return false
            }
        }
        catch (e: Exception) {
            // Handle network errors
            println("Network Error")
            return false
        }
    }
}

