package com.example.foodpanda_capstone.model

import com.example.foodpanda_capstone.model.mock_data.playlistList
import com.example.foodpanda_capstone.model.mock_data.userPlaylist
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PlaylistRepository {
    fun fetchAllPlaylist(): Flow<AllPlaylist> = flow {
        // TODO: Add retrofit call here when backend is ready

        emit(playlistList)
    }

    fun fetchOnePlaylist(playlistId: Int): Flow<Playlist> = flow {
        // TODO: Add retrofit call here when backend is ready

        emit(userPlaylist[playlistId])
    }
}
