package com.example.foodpanda_capstone.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class GeneralViewModelFactory<T: ViewModel, R>(
    private val viewModelClass: Class<T>,
    private val factory: (R) -> T, //  higher-order function that creates an instance of our ViewModel type T given an instance of R.
    private val repository: R
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(viewModelClass)){
            @Suppress("UNCHECKED_CAST")
            return factory(repository) as T
        }
        throw IllegalArgumentException("${viewModelClass.simpleName} Class not found")
    }
}
