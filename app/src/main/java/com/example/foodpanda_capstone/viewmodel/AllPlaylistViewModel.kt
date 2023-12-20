package com.example.foodpanda_capstone.viewmodel

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

class AllPlaylistViewModel(private val repository: PlaylistRepository): ViewModel() {

    private val _publicPlaylists = MutableStateFlow<List<PlaylistCategory>>(emptyList())
    val publicPlaylists: StateFlow<List<PlaylistCategory>> = _publicPlaylists

    private val _userPlaylists = MutableStateFlow<List<Playlist>>(emptyList())
    val userPlaylists: StateFlow<List<Playlist>> = _userPlaylists

    init {
        getAllPlaylist()
    }

    private fun getAllPlaylist() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchAllPlaylist().collect { playlists ->
                _publicPlaylists.value = playlists.publicPlaylist
                _userPlaylists.value = playlists.userPlaylist
            }
        }
    }
}
