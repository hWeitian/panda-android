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

class AllPlaylistViewModel(private val repository: PlaylistRepository): ViewModel() {

    private val _publicPlaylists = MutableLiveData<List<PlaylistCategory>>()
    val publicPlaylists: LiveData<List<PlaylistCategory>> = _publicPlaylists

    private val _userPlaylists = MutableLiveData<List<Playlist>>()
    val userPlaylists: LiveData<List<Playlist>> = _userPlaylists

    init {
        getAllPlaylist()
    }

    private fun getAllPlaylist() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchAllPlaylist().collect { playlists ->
                _publicPlaylists.postValue(playlists.publicPlaylist)
                _userPlaylists.postValue(playlists.userPlaylist)
            }
        }
    }
}
