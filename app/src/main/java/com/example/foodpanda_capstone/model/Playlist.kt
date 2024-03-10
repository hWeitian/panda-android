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

data class FinalPlaylist(
    val id: Int,
    val name: String,
    val imageUrl: String?,
    val cost: BigDecimal,
    val deliveryDay: String?,
    val foodItems: List<RestaurantFoodItems?>?,
    val isPublic: Boolean?,
    val deliverTime: String?,
    val userId: String,
    val status: String = "Subscribed"
)

//data class PlaylistCategory(
//    val categoryTitle: String,
//    val list: List<Playlist>
//)

data class PlaylistOverview (
    val id: Int,
    val name: String,
    val imageUrl: String?,
    val isPublic: Boolean?,
    val deliveryDay: String,
    val category: String,
    val cost: BigDecimal,
)

data class AllPlaylist(
    val publicPlaylist: List<PlaylistOverview>,
    val userPlaylist: List<PlaylistOverview>
)

data class Days (
    val name: String,
    var isSelected: Boolean
)
