package com.example.foodpanda_capstone.model

data class Playlist (
    val id: Int,
    val name: String,
    val imageUrl: String,
    val cost: Float,
    val deliverDay: String,
)

data class PlaylistCategory (
    val categoryTitle: String,
    val list: List<Playlist>
)

data class AllPlaylist (
    val publicPlaylist: List<PlaylistCategory>,
    val userPlaylist: List<Playlist>
)
