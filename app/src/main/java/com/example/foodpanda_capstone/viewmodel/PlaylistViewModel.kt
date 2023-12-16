package com.example.foodpanda_capstone.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodpanda_capstone.model.Playlist
import com.example.foodpanda_capstone.model.PlaylistCategory
import com.example.foodpanda_capstone.model.PlaylistRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout

class PlaylistViewModel(private val repository: PlaylistRepository) : ViewModel() {

    private val _currentPlaylist = MutableLiveData<Playlist>()
    val currentPlaylist: LiveData<Playlist> = _currentPlaylist

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    fun getOnePlaylist(playlistId: Int) {
        viewModelScope.launch(Dispatchers.IO) {

            withContext(Dispatchers.Main) {
                _isLoading.value = true
            }

            repository.fetchOnePlaylist(playlistId).collect { playlist ->
                _currentPlaylist.postValue(playlist)
            }

            withContext(Dispatchers.Main) {
                _isLoading.value = false
            }
        }
    }

}
