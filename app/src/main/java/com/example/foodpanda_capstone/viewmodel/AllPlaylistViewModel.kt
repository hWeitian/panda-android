package com.example.foodpanda_capstone.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodpanda_capstone.model.PlaylistOverview
import com.example.foodpanda_capstone.model.PlaylistRepository
import com.example.foodpanda_capstone.utils.logErrorMsg
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AllPlaylistViewModel(private val repository: PlaylistRepository) : ViewModel() {

    private val _publicPlaylists = MutableStateFlow<List<PlaylistOverview>>(emptyList())
    val publicPlaylists: StateFlow<List<PlaylistOverview>> = _publicPlaylists

    private val _userPlaylists = MutableStateFlow<List<PlaylistOverview>>(emptyList())
    val userPlaylists: StateFlow<List<PlaylistOverview>> = _userPlaylists

    private val _cancelledPlaylist = MutableStateFlow<List<PlaylistOverview>>(emptyList())
    val cancelledPlaylist: StateFlow<List<PlaylistOverview>> = _cancelledPlaylist

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun getAllPlaylist(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            while (publicPlaylists.value.isEmpty()) {
                withContext(Dispatchers.Main) {
                    _isLoading.value = true
                    delay(1000)
                }
                try {
                    repository.fetchAllPlaylist(userId).collect { playlists ->
                        _publicPlaylists.value = playlists.publicPlaylist.filter { it.status != "Cancelled" }
                        _userPlaylists.value = playlists.userPlaylist.filter { it.status != "Cancelled" }
                        _cancelledPlaylist.value = playlists.userPlaylist.filter { it.status == "Cancelled" }
                    }
                } catch (e: Exception) {
                    logErrorMsg("getAllPlaylist", e)
                }
                if (publicPlaylists.value.isNotEmpty()) {
                    withContext(Dispatchers.Main) {
                        _isLoading.value = false
                    }
                }
            }
        }
    }
}
