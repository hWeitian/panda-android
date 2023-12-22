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
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PlaylistSectionViewModel (private val repository: PlaylistRepository): ViewModel() {

    private val _categoryPlaylist = MutableStateFlow<List<PlaylistCategory>>(emptyList())
    val categoryPlaylist: StateFlow<PlaylistCategory?> = _categoryPlaylist
        .map {  it.firstOrNull() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

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
                Log.e("PdError", "Error at fetchCategoryPlaylist - ${e.message}")
            }


            withContext(Dispatchers.Main) {
                _isLoading.value = false
            }

        }
    }

}
