package com.example.foodpanda_capstone.model

import android.util.Log
import com.example.foodpanda_capstone.model.api.PlaylistApiService

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class PlaylistRepository(private val apiService: PlaylistApiService) {

    fun fetchAllPlaylist(userId: String): Flow<AllPlaylist> = flow {
        val result = apiService.getAllPlaylist(userId)
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

    fun fetchAllPublicPlaylist(): Flow<List<PlaylistOverview>> = flow {
        val result = apiService.getAllPubliPlaylist()
        emit(result)
    }.catch { e ->
        Log.e("PdError", "Error at fetchAllPublicPlaylist - ${e.message}")
    }

    fun fetchAllUserPlaylist(userId: String): Flow<List<PlaylistOverview>> = flow {
        val result = apiService.getAllUserPlaylist(userId)
        emit(result)
    }.catch { e ->
        Log.e("PdError", "Error at fetchAllUserPlaylist - ${e.message}")
    }


    // TODO: Remove mockData once backend is ready
    private var mockData = listOf(
        RecentSearch(1, "Burger"),
        RecentSearch(2, "Korean"),
        RecentSearch(3, "Sushi"),
    )

    fun fetchRecentSearch(userId: String): Flow<List<RecentSearch>> = flow {
        val result = apiService.getRecentSearch(userId)
        emit(result)
    }.catch { e ->
        Log.e("PdError", "Error at fetchRecentSearch - ${e.message}")
    }

    suspend fun deleteRecentSearch(userId: String, keyword: String) {
        apiService.deleteRecentSearch(userId, keyword)
    }

    fun fetchSearchResults(userId: String, keyword: String): Flow<List<FoodItem>> = flow {
        val result = apiService.getSearchResults(userId, keyword)
        emit(result)
    }.catch { e ->
        throw e
        Log.e("PdError", "Error at fetchSearchResults - ${e.message}")
    }

    fun fetchRandomPlaylist(cuisine: String, numOfDish: Int, maxBudget: Int) = flow {
        val result = apiService.getRandomPlaylist(cuisine, numOfDish, maxBudget)
        emit(result)
    }.catch { e ->
        throw e
        Log.e("PdError", "Error at fetchRandomPlaylist - ${e.message}")
    }

    suspend fun subscribePlaylist(playlist: FinalPlaylist, userId: String) {
        apiService.postPlaylist(playlist, userId)
    }

    suspend fun amendPlaylist(playlist: FinalPlaylist, userId: String) {
        apiService.updatePlaylist(playlist, userId)
    }

    suspend fun cancelSubscription(userId: String, playlistId: Int) {
        apiService.cancelSubscription(userId, playlistId)
    }
}
