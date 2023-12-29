package com.example.foodpanda_capstone.model.api

import com.example.foodpanda_capstone.model.AllPlaylist
import com.example.foodpanda_capstone.model.Playlist
import com.example.foodpanda_capstone.model.PlaylistCategory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PlaylistApiService {
    @GET("playlists/latest")
    suspend fun getAllPlaylist(): AllPlaylist

    @GET("playlists/current/{id}")
    suspend fun getOnePlaylist(@Path("id") id: Int): Playlist

    @GET("playlists/category/{category}")
    suspend fun getCategoryPlaylist(@Path("category") category: String): PlaylistCategory
}
