package com.example.foodpanda_capstone.model.api

import com.example.foodpanda_capstone.model.AllPlaylist
import retrofit2.http.GET

interface PlaylistApiService {
    @GET("playlists/latest")
    suspend fun getAllPlaylist(): AllPlaylist
}
