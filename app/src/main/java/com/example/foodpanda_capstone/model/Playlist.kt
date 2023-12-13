package com.example.foodpanda_capstone.model

data class Playlist (
    val id: Int,
    val name: String,
    val description: String,
    val imageUrl: String,
    val isPublic: Boolean,
    val cost: Float,
    val deliverDay: String
)

val playlistList = listOf<Playlist>(
    Playlist(
        id = 1,
        name = "Playlist 1",
        description = "This is playlist 1",
        imageUrl = "https://picsum.photos/200/200",
        isPublic = true,
        cost = 30.00F,
        deliverDay = "Thu"
    ),
    Playlist(
        id = 2,
        name = "Playlist 2",
        description = "This is playlist 2",
        imageUrl = "https://picsum.photos/200/200",
        isPublic = true,
        cost = 50.00F,
        deliverDay = "Fri"
    ),
    Playlist(
        id = 3,
        name = "Playlist 3",
        description = "This is playlist 3",
        imageUrl = "https://picsum.photos/200/200",
        isPublic = true,
        cost = 15.90F,
        deliverDay = "Tue"
    ),
    Playlist(
        id = 4,
        name = "Playlist 4",
        description = "This is playlist 4",
        imageUrl = "https://picsum.photos/200/200",
        isPublic = true,
        cost = 20.90F,
        deliverDay = "Wed"
    ),
    Playlist(
        id = 5,
        name = "Playlist 5",
        description = "This is playlist 5",
        imageUrl = "https://picsum.photos/200/200",
        isPublic = true,
        cost = 10.00F,
        deliverDay = "Sun"
    )
)
