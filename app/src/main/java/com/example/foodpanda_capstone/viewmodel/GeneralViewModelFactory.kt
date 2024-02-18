package com.example.foodpanda_capstone.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class GeneralViewModelFactory<T : ViewModel, R>(
    private val viewModelClass: Class<T>,
    private val factory: (R) -> T, // Factory function with one parameter
    private val repository: R
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(viewModelClass)) {
            @Suppress("UNCHECKED_CAST")
            return factory(repository) as T
        }
        throw IllegalArgumentException("${viewModelClass.simpleName} Class not found")
    }
}
class GeneralViewModelFactoryDoubleParam<T : ViewModel, R1, R2>(
    private val viewModelClass: Class<T>,
    private val factory: (R1, R2) -> T,
    private val repository: R1,
    private val context: R2
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(viewModelClass)) {
            @Suppress("UNCHECKED_CAST")
            return factory(repository, context) as T
        }
        throw IllegalArgumentException("${viewModelClass.simpleName} Class not found")
    }
}
