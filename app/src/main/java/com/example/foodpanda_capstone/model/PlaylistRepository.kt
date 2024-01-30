package com.example.foodpanda_capstone.model

import android.util.Log
import com.example.foodpanda_capstone.model.api.PlaylistApiService
import com.example.foodpanda_capstone.viewmodel.logErrorMsg

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class PlaylistRepository(private val apiService: PlaylistApiService) {

    fun fetchAllPlaylist(): Flow<AllPlaylist> = flow {
        val result = apiService.getAllPlaylist()
        emit(result)
    }.catch { e ->
        Log.e("PdError", "Error at fetchAllPlaylist - ${e.message}")
    }

    fun fetchOnePlaylist(playlistId: Int): Flow<Playlist> = flow {
        val result = apiService.getOnePlaylist(playlistId)
        emit(result)
    }.catch { e ->
        Log.e("PdError", "Error at fetchOnePlaylist - ${e.message}")
    }

    fun fetchCategoryPlaylist(category: String): Flow<PlaylistCategory> = flow {
        val result = apiService.getCategoryPlaylist(category)
        emit(result)
    }.catch { e ->
        Log.e("PdError", "Error at fetchCategoryPlaylist - ${e.message}")
    }

    // TODO: Remove mockData once backend is ready
    private var mockData = listOf(
        RecentSearch(1, "Burger"),
        RecentSearch(2, "Korean"),
        RecentSearch(3, "Sushi"),
    )
    fun fetchRecentSearch(userId: Int): Flow<List<RecentSearch>> = flow {
        val result = mockData // TODO: Add function to get recent search
        emit(result)
    }.catch { e ->
        Log.e("PdError", "Error at fetchRecentSearch - ${e.message}")
    }

    fun deleteRecentSearch(userId: Int, keyword: String) {
        // TODO: Add function to delete recent search from database
        mockData = mockData.filterNot { it.keyword == keyword }
    }

    fun fetchSearchResults(keyword: String): Flow<List<FoodItem>> = flow {
        val result = apiService.getSearchResults(keyword)
        emit(result)
    }.catch { e ->
        Log.e("PdError", "Error at fetchSearchResults - ${e.message}")
    }
}
