package com.example.foodpanda_capstone.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodpanda_capstone.model.PlaylistOverview
import com.example.foodpanda_capstone.model.PlaylistRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PlaylistSectionViewModel(private val repository: PlaylistRepository) : ViewModel() {

    private val _categoryPlaylist = MutableStateFlow<List<PlaylistOverview>>(emptyList())
    val categoryPlaylist: StateFlow<List<PlaylistOverview>> = _categoryPlaylist

    private val _cancelledPlaylist = MutableStateFlow<List<PlaylistOverview>>(emptyList())
    val cancelledPlaylist: StateFlow<List<PlaylistOverview>> = _cancelledPlaylist

    private val _otherPlaylist = MutableStateFlow<List<PlaylistOverview>>(emptyList())
    val otherPlaylist: StateFlow<List<PlaylistOverview>> = _otherPlaylist

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
                        _cancelledPlaylist.value = playlists.filter { it.status == "Cancelled" }
                        _otherPlaylist.value = playlists.filter { it.status != "Cancelled" }
                        _categoryPlaylist.value = playlists
//                        println(_cancelledPlaylist.value)
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

    private fun filterPlaylist(
        status: String,
        secondStatus: String = "",
        completeList: List<PlaylistOverview>
    ): List<PlaylistOverview> {
        println(completeList)
        var result = mutableListOf<PlaylistOverview>()
        for (playlistOverview in completeList) {
            if (playlistOverview.status == status || playlistOverview.status == secondStatus) {
                result.add(playlistOverview)
            }
        }
        println(result)
        return result
    }
}


