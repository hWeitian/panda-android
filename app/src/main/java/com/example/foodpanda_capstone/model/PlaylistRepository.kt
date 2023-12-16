package com.example.foodpanda_capstone.model

import android.util.Log
import com.example.foodpanda_capstone.model.mock_data.playlistList
import com.example.foodpanda_capstone.model.mock_data.userPlaylist
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class PlaylistRepository {
    suspend fun fetchAllPlaylist(): Flow<AllPlaylist> = flow {
        // TODO: Add retrofit call here when backend is ready

        emit(playlistList)
    }.catch { e ->
        Log.e("PdError", "Error at fetchAllPlaylist - ${e.message}")
    }

    suspend fun fetchOnePlaylist(playlistId: Int): Flow<Playlist> = flow {
        // TODO: Add retrofit call here when backend is ready
        delay(2000L)
        emit(userPlaylist[playlistId])
    }.catch { e ->
        Log.e("PdError", "Error at fetchOnePlaylist - ${e.message}")
    }
}
