package com.example.foodpanda_capstone.model

import com.example.foodpanda_capstone.model.mock_data.playlistList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PlaylistRepository {
    fun fetchAllPlaylist(): Flow<AllPlaylist> = flow {
        // TODO: Add retrofit call here when backend is ready
        emit(playlistList)
    }
}
