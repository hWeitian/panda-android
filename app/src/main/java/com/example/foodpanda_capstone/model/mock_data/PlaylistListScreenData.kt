package com.example.foodpanda_capstone.model.mock_data

import com.example.foodpanda_capstone.model.AllPlaylist
import com.example.foodpanda_capstone.model.Playlist
import com.example.foodpanda_capstone.model.PlaylistCategory

val publicPlaylist = listOf(
    PlaylistCategory(
        categoryTitle = "K-pop",
        list = listOf(
            Playlist(
                id = 1,
                name = "K-pop 1",
                imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=3160&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                cost = 30.00F,
                deliveryDay = "Thu",
                foodItems= null,
                isPublic = true
            ),
            Playlist(
                id = 2,
                name = "K-pop 2",
                imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=3160&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                cost = 50.00F,
                deliveryDay = "Fri",
                foodItems= null,
                isPublic = true
            ),
            Playlist(
                id = 3,
                name = "K-pop 3",
                imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=3160&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                cost = 15.90F,
                deliveryDay = "Tue",
                foodItems= null,
                isPublic = true
            )
        )
    ),
    PlaylistCategory(
        categoryTitle = "Summer’s Arrival",
        list = listOf(
            Playlist(
                id = 4,
                name = "Summer 4",
                imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=3160&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                cost = 20.90F,
                deliveryDay = "Wed",
                foodItems= null,
                isPublic = true
            ),
            Playlist(
                id = 5,
                name = "Summer 5",
                imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=3160&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                cost = 10.00F,
                deliveryDay = "Sun",
                foodItems= null,
                isPublic = true
            ),
            Playlist(
                id = 6,
                name = "Summer 6",
                imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=3160&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                cost = 20.00F,
                deliveryDay = "Sun",
                foodItems= null,
                isPublic = true
            )
        )
    )
)

val userPlaylist = listOf(
    Playlist(
        id = 7,
        name = "User Playlist 1",
        imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=3160&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        cost = 10.00F,
        deliveryDay = "Wed",
        foodItems= null,
        isPublic = false
    ),
    Playlist(
        id = 8,
        name = "User Playlist 2",
        imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=3160&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        cost = 30.00F,
        deliveryDay = "Fri",
        foodItems= null,
        isPublic = false
    ),
    Playlist(
        id = 9,
        name = "User Playlist 3",
        imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=3160&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        cost = 70.00F,
        deliveryDay = "Sun",
        foodItems= null,
        isPublic = false
    ),
    Playlist(
        id = 10,
        name = "User Playlist 4",
        imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=3160&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        cost = 50.00F,
        deliveryDay = "Sat",
        foodItems= null,
        isPublic = false
    ),
)

val playlistList = AllPlaylist(publicPlaylist, userPlaylist)
