package com.example.foodpanda_capstone.model

import android.util.Log
import com.example.foodpanda_capstone.model.api.PlaylistApiService
import com.example.foodpanda_capstone.model.api.RetrofitInstance
import com.example.foodpanda_capstone.model.mock_data.categoryPlaylists
import com.example.foodpanda_capstone.model.mock_data.playlistList

import com.example.foodpanda_capstone.model.mock_data.privatePlaylist
import com.example.foodpanda_capstone.model.mock_data.publicPlaylist1

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class PlaylistRepository {

    private val apiService: PlaylistApiService
    private val retrofitInstance: RetrofitInstance = RetrofitInstance()

    init {
        apiService = retrofitInstance.getService()
    }

    fun fetchAllPlaylist(): Flow<AllPlaylist> = flow {
        // TODO: Add retrofit call here when backend is ready
        val result = apiService.getAllPlaylist()
        //playlistList
        emit(result)
    }.catch { e ->
        Log.e("PdError", "Error at fetchAllPlaylist - ${e.message}")
    }

    suspend fun fetchOnePlaylist(playlistId: Int): Playlist {

        // TODO: Add retrofit call here when backend is ready

//        delay(2000L)
        return privatePlaylist // privatePlaylist or publicPlaylist1 for testing
    }

    suspend fun fetchCategoryPlaylist(category: String): List<PlaylistCategory> {
        return categoryPlaylists
    }
}
