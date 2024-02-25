package com.example.foodpanda_capstone.model

import android.util.Log
import com.example.foodpanda_capstone.model.api.LoginFormApiClient.apiService
import com.example.foodpanda_capstone.model.api.LoginFormApiService
import com.example.foodpanda_capstone.utils.UserUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.tasks.await

class AuthRepository(private val auth: FirebaseAuth) {
    suspend fun signUp(firstName: String, lastName: String, email: String, password: String) {
        val userCredential = auth.createUserWithEmailAndPassword(email, password).await()
        val user = userCredential.user

        // Set the userUID in UserUtils
        user?.let { UserUtils.setUserUID(it.uid) }
        var userUIDsaved = UserUtils.getUserUID()

        Log.d("UserUtils", "This is the userUID: $userUIDsaved")
        // Update user profile with first name and last name
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName("$firstName $lastName")
            .build()

        user?.updateProfile(profileUpdates)?.await()
        user?.let {
            val loginForm = LoginForm(it.uid, firstName, lastName, email, password)
//            apiService.createUser(loginForm)
        }
        // TODO: Store Model in Backend as a Create method
        // HTTP put request with uid, firstname lastname email password as parameter

    }

    suspend fun signIn(email: String, password: String): Result<Boolean> {
        return try {
            val userCredential = auth.signInWithEmailAndPassword(email, password).await()
            val user = userCredential.user
            user?.let { UserUtils.setUserUID(it.uid) }

            var getUserUIDsaved = UserUtils.getUserUID()
//            var getUserUIDsaved = UserUtils.getUserUID()
//            Log.d("GetUserUtils", "This is the userUID: $getUserUIDsaved")

            Result.success(true)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun signOut() {
        auth.signOut()
    }

    fun getCurrentUser() = auth.currentUser

}
