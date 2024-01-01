package com.example.foodpanda_capstone.model

import java.math.BigDecimal

data class Playlist(
    val id: Int,
    val name: String,
    val imageUrl: String?,
    val cost: BigDecimal,
    val deliveryDay: String,
    val foodItems: List<RestaurantFoodItems?>?,
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
