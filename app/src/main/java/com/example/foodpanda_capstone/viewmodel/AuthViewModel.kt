package com.example.foodpanda_capstone.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodpanda_capstone.model.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _loginState = MutableStateFlow(false)
    val loginState: StateFlow<Boolean> = _loginState

    private val _signupState = MutableStateFlow(false)
    val signupState: StateFlow<Boolean> = _signupState

    private val _navigateToHome = MutableStateFlow(false)
    val navigateToHome: StateFlow<Boolean> = _navigateToHome


    //sus and await?
    fun signUp(firstName: String, lastName: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                 authRepository.signUp(firstName, lastName, email, password)
                _signupState.value = true  // Signal signup success
            } catch (e: Exception) {
                // Handle signup failure if needed
                _signupState.value = false
            }
        }
    }


    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            val signInResult = authRepository.signIn(email, password)
            _loginState.value = signInResult.isSuccess
        }
    }

    fun onNavigationComplete() {
        _navigateToHome.value = false
    }

    fun signOut() {
        authRepository.signOut()
        _loginState.value = false
    }

    fun getCurrentUser() = authRepository.getCurrentUser()

}
