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
        imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=3160&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        isPublic = true,
        cost = 30.00F,
        deliverDay = "Thu"
    ),
    Playlist(
        id = 2,
        name = "Playlist 2",
        description = "This is playlist 2",
        imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=3160&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        isPublic = true,
        cost = 50.00F,
        deliverDay = "Fri"
    ),
    Playlist(
        id = 3,
        name = "Playlist 3",
        description = "This is playlist 3",
        imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=3160&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        isPublic = true,
        cost = 15.90F,
        deliverDay = "Tue"
    ),
    Playlist(
        id = 4,
        name = "Playlist 4",
        description = "This is playlist 4",
        imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=3160&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        isPublic = true,
        cost = 20.90F,
        deliverDay = "Wed"
    ),
    Playlist(
        id = 5,
        name = "Playlist 5",
        description = "This is playlist 5",
        imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=3160&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        isPublic = true,
        cost = 10.00F,
        deliverDay = "Sun"
    )
)
