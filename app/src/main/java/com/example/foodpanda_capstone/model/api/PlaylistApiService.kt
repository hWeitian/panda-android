package com.example.foodpanda_capstone.model.api

import com.example.foodpanda_capstone.model.AllPlaylist
import com.example.foodpanda_capstone.model.FinalPlaylist
import com.example.foodpanda_capstone.model.FoodItem
import com.example.foodpanda_capstone.model.Playlist
import com.example.foodpanda_capstone.model.PlaylistOverview
import com.example.foodpanda_capstone.model.RecentSearch
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface PlaylistApiService {
    @GET("playlists-latestv2")
    suspend fun getAllPlaylist(@Query("user_id") user_id: String): AllPlaylist

    @GET("playlists/current/{id}")
    suspend fun getOnePlaylist(@Path("id") id: Int): Playlist

    @GET("userplaylists/{userId}")
    suspend fun getAllUserPlaylist(@Path("userId") userId: String): List<PlaylistOverview>
    @GET("publicplaylists")
    suspend fun getAllPubliPlaylist(): List<PlaylistOverview>

    @GET("playlists/random/{cuisine}/{numOfDishes}/{maxBudget}")
    suspend fun getRandomPlaylist(
        @Path("cuisine") cuisine: String,
        @Path("numOfDishes") numOfDishes: Int,
        @Path("maxBudget") maxBudget: Int
    ): Playlist

    @GET("search/{userId}/{keyword}")
    suspend fun getSearchResults(@Path("userId") userId: String, @Path("keyword") keyword: String): List<FoodItem>

    @GET("recent-search/{userId}")
    suspend fun getRecentSearch(@Path("userId") userId: String): List<RecentSearch>

    @DELETE("recent-search/{userId}/{keyword}")
    suspend fun deleteRecentSearch(@Path("userId") userId: String, @Path("keyword") keyword: String)

    @POST("playlist/add/{userId}")
    suspend fun postPlaylist(@Body body: FinalPlaylist, @Path("userId") userId: String)

    @PUT("playlist/update/{userId}")
    suspend fun updatePlaylist(@Body body: FinalPlaylist, @Path("userId") userId: String)

    @PUT("playlist/{userId}/{playlistId}")
    suspend fun cancelSubscription(@Path("userId") userId: String, @Path("playlistId") playlistId: Int)
}


//interface PlaylistApiService {
//    @GET("playlists/latest/{userId}")
//    suspend fun getAllPlaylist(@Path("userId") userId: String): AllPlaylist
//
//    @GET("playlists/current/{id}")
//    suspend fun getOnePlaylist(@Path("id") id: Int): Playlist
//
//    @GET("playlists/user/{userId}")
//    suspend fun getAllUserPlaylist(@Path("userId") userId: String): List<PlaylistOverview>
//    @GET("playlists/public")
//    suspend fun getAllPubliPlaylist(): List<PlaylistOverview>
//
//    @GET("playlists/random/{cuisine}/{numOfDishes}/{maxBudget}")
//    suspend fun getRandomPlaylist(
//        @Path("cuisine") cuisine: String,
//        @Path("numOfDishes") numOfDishes: Int,
//        @Path("maxBudget") maxBudget: Int
//    ): Playlist
//
//    @GET("dishes/search/{keyword}")
//    suspend fun getSearchResults(@Path("keyword") keyword: String): List<FoodItem>
//
//    @DELETE("search/{userId}/{keyword}")
//    suspend fun deleteRecentSearch(@Path("userId") userId: Int, @Path("keyword") keyword: String)
//
//    @POST("playlists/add/{userId}")
//    suspend fun postPlaylist(@Body body: FinalPlaylist, @Path("userId") userId: String)
//}
