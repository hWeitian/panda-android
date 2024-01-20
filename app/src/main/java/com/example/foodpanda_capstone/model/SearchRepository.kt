package com.example.foodpanda_capstone.model

import android.util.Log
import com.example.foodpanda_capstone.model.api.PlaylistApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class SearchRepository(private val apiService: PlaylistApiService) {

    private val mockData = listOf(
        RecentSearch(1, "Hamburger"),
        RecentSearch(2, "Korean"),
        RecentSearch(3, "Sushi"),
    )
    fun fetchRecentSearch(userId: Int): Flow<List<RecentSearch>> = flow {
        val result = mockData // TODO: Add function to get recent search
        emit(result)
    }.catch { e ->
        Log.e("PdError", "Error at fetchRecentSearch - ${e.message}")
    }

}
