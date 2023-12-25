package com.example.foodpanda_capstone.model

import com.google.gson.annotations.SerializedName

data class Playlist(
    val id: Int,
    val name: String,
    val imageUrl: String?,
    val cost: Float,
    val deliveryDay: String,
    val foodItems: List<RestaurantFoodItems>?,
    val isPublic: Boolean?
)

data class PlaylistCategory(
    val categoryTitle: String,
    val list: List<Playlist>
)

data class AllPlaylist(
    val publicPlaylist: List<PlaylistCategory>,
    val userPlaylist: List<Playlist>
)



//data class Playlist2 (
//    @SerializedName("id") val id: Int,
//    @SerializedName("name") val name: String,
//    @SerializedName("description") val description: String,
//    @SerializedName("category") val category: String,
//    @SerializedName("imageUrl") val imageUrl: String,
//    @SerializedName("isPublic") val isPublic: Boolean,
//    @SerializedName("deliveryDay") val deliveryDay: String,
//)
