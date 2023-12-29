package com.example.foodpanda_capstone.model

import android.util.Log
import com.example.foodpanda_capstone.model.api.PlaylistApiService
import com.example.foodpanda_capstone.model.api.RetrofitInstance
import com.example.foodpanda_capstone.model.mock_data.categoryPlaylists
import com.example.foodpanda_capstone.viewmodel.logErrorMsg

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
        val result = apiService.getAllPlaylist()
        emit(result)
    }.catch { e ->
        Log.e("PdError", "Error at fetchAllPlaylist - ${e.message}")
    }

    suspend fun fetchOnePlaylist(playlistId: Int): Playlist {
        return apiService.getOnePlaylist(playlistId)
    }

    suspend fun fetchCategoryPlaylist(category: String): PlaylistCategory {
        return apiService.getCategoryPlaylist(category)
    }

}
