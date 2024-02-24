package com.example.foodpanda_capstone.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodpanda_capstone.model.PlaylistCategory
import com.example.foodpanda_capstone.model.PlaylistOverview
import com.example.foodpanda_capstone.model.PlaylistRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PlaylistSectionViewModel(private val repository: PlaylistRepository) : ViewModel() {

    private val _categoryPlaylist = MutableStateFlow<List<PlaylistOverview>>(emptyList())
    val categoryPlaylist: StateFlow<List<PlaylistOverview>> = _categoryPlaylist

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun getPublicPlaylist() {
        viewModelScope.launch(Dispatchers.IO) {
            while (categoryPlaylist.value.isEmpty()) {
                withContext(Dispatchers.Main) {
                    _isLoading.value = true
                    delay(1000)
                }
                try {
                    repository.fetchAllPublicPlaylist().collect { playlists ->
                        _categoryPlaylist.value = playlists
                    }
                } catch (e: Exception) {
                    logErrorMsg("getPublicPlaylist", e)
                }
                if (categoryPlaylist.value.isNotEmpty()) {
                    withContext(Dispatchers.Main) {
                        _isLoading.value = false
                    }
                }
            }
        }
    }

    fun getUserPlaylist(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            while (categoryPlaylist.value.isEmpty()) {
                withContext(Dispatchers.Main) {
                    _isLoading.value = true
                    delay(1000)
                }
                try {
                    repository.fetchAllUserPlaylist(userId).collect { playlists ->
                        _categoryPlaylist.value = playlists
                    }
                } catch (e: Exception) {
                    logErrorMsg("getUserPlaylist", e)
                }
                if (categoryPlaylist.value.isNotEmpty()) {
                    withContext(Dispatchers.Main) {
                        _isLoading.value = false
                    }
                }
            }
        }
    }
}


