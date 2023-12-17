package com.example.foodpanda_capstone.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodpanda_capstone.model.Playlist
import com.example.foodpanda_capstone.model.PlaylistCategory
import com.example.foodpanda_capstone.model.PlaylistRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout

class PlaylistViewModel(private val repository: PlaylistRepository) : ViewModel() {

    private val _currentPlaylist = MutableStateFlow<Playlist?>(null)
    val currentPlaylist: MutableStateFlow<Playlist?> = _currentPlaylist

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun getOnePlaylist(playlistId: Int) {

            viewModelScope.launch(Dispatchers.IO) {

                withContext(Dispatchers.Main) {
                    _isLoading.value = true
                }

                try {

                    val result = repository.fetchOnePlaylist(playlistId)
                    _currentPlaylist.value = result

                } catch (e: Exception){
                    Log.e("PdError", "Error at fetchOnePlaylist - ${e.message}")
                }


                withContext(Dispatchers.Main) {
                    _isLoading.value = false
                }
            }

    }

}
