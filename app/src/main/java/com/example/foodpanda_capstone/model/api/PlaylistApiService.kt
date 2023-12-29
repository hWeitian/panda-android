package com.example.foodpanda_capstone.model.api

import com.example.foodpanda_capstone.model.AllPlaylist
import com.example.foodpanda_capstone.model.Playlist
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PlaylistApiService {
    @GET("playlists/latest")
    suspend fun getAllPlaylist(): AllPlaylist

    @GET("playlists/current/{id}")
    suspend fun getOnePlaylist(@Path("id") id: Int): Playlist

}
