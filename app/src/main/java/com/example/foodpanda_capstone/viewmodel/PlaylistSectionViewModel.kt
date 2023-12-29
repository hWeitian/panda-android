package com.example.foodpanda_capstone.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodpanda_capstone.model.PlaylistCategory
import com.example.foodpanda_capstone.model.PlaylistRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PlaylistSectionViewModel (private val repository: PlaylistRepository): ViewModel() {

    private val _categoryPlaylist = MutableStateFlow<PlaylistCategory?>(null)
    val categoryPlaylist: StateFlow<PlaylistCategory?> = _categoryPlaylist.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun getCategoryPlaylist(categoryName: String){
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                _isLoading.value = true
            }
            try {
                val result = repository.fetchCategoryPlaylist(categoryName)
                _categoryPlaylist.value = result

            } catch (e: Exception){
                logErrorMsg("getCategoryPlaylist" , e)
            }
            withContext(Dispatchers.Main) {
                _isLoading.value = false
            }

        }
    }

}
