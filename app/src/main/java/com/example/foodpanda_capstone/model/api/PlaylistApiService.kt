package com.example.foodpanda_capstone.model.api

import com.example.foodpanda_capstone.model.AllPlaylist
import com.example.foodpanda_capstone.model.FoodItem
import com.example.foodpanda_capstone.model.Playlist
import com.example.foodpanda_capstone.model.PlaylistCategory
import com.example.foodpanda_capstone.model.PlaylistOverview
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PlaylistApiService {
    @GET("playlists/latest/{userId}")
    suspend fun getAllPlaylist(@Path("userId") userId: String): AllPlaylist

    @GET("playlists/current/{id}")
    suspend fun getOnePlaylist(@Path("id") id: Int): Playlist

    @GET("playlists/category/{category}")
    suspend fun getCategoryPlaylist(@Path("category") category: String): List<PlaylistOverview>

    @GET("dishes/search/{keyword}")
    suspend fun getSearchResults(@Path("keyword") keyword: String): List<FoodItem>

    @DELETE("search/{userId}/{keyword}")
    suspend fun deleteRecentSearch(@Path("userId") userId: Int, @Path("keyword") keyword: String)
}
