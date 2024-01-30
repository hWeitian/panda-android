package com.example.foodpanda_capstone.model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.tasks.await

class AuthRepository(private val auth: FirebaseAuth) {
    suspend fun signUp( firstName: String, lastName: String, email: String, password: String) {
        val userCredential = auth.createUserWithEmailAndPassword(email, password).await()
        val user = userCredential.user

        // Update user profile with first name and last name
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName("$firstName $lastName")
            .build()
        user?.updateProfile(profileUpdates)?.await()
    }

    suspend fun signIn(email: String, password: String): Result<Boolean> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
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
