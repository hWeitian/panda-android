package com.example.foodpanda_capstone.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodpanda_capstone.model.LoginFormRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginFormViewModel(private val userRepository: LoginFormRepository) : ViewModel() {

    private val _loginState = MutableStateFlow(false)
    val loginState: StateFlow<Boolean> = _loginState


    private val _navigateToHome = MutableStateFlow(false)
    val navigateToHome: StateFlow<Boolean> = _navigateToHome

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = userRepository.login(email, password)
            if (_loginState.value) {
                _navigateToHome.value = true
//                _navigateToHome.value = false
            }

        }
    }

    fun onNavigationComplete() {
        _navigateToHome.value = false
    }

    fun logout() {
        _loginState.value = false
        Log.d("Navigation", "isLoggedIn: $_loginState")

    }
}
