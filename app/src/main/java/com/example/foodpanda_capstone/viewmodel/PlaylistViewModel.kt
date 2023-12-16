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

class PlaylistViewModel(private val repository: PlaylistRepository): ViewModel() {

    private val _playlist = MutableLiveData<Playlist>()
    val publicPlaylist: LiveData<Playlist> = _playlist

    fun getOnePlaylist(playlistId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchOnePlaylist(0).collect { playlist ->
                _playlist.postValue(playlist)
            }
        }
    }

}
