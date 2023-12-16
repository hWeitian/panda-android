package com.example.foodpanda_capstone.model.mock_data

import com.example.foodpanda_capstone.model.AllPlaylist
import com.example.foodpanda_capstone.model.Playlist
import com.example.foodpanda_capstone.model.PlaylistCategory

//val playlistList = listOf<Playlist>(
//    Playlist(
//        id = 1,
//        name = "Playlist 1",
//        imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=3160&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
//        isPublic = true,
//        cost = 30.00F,
//        deliverDay = "Thu"
//    ),
//    Playlist(
//        id = 2,
//        name = "Playlist 2",
//        imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=3160&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
//        isPublic = true,
//        cost = 50.00F,
//        deliverDay = "Fri"
//    ),
//    Playlist(
//        id = 3,
//        name = "Playlist 3",
//        imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=3160&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
//        isPublic = true,
//        cost = 15.90F,
//        deliverDay = "Tue"
//    ),
//    Playlist(
//        id = 4,
//        name = "Playlist 4",
//        imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=3160&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
//        isPublic = true,
//        cost = 20.90F,
//        deliverDay = "Wed"
//    ),
//    Playlist(
//        id = 5,
//        name = "Playlist 5",
//        imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=3160&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
//        isPublic = true,
//        cost = 10.00F,
//        deliverDay = "Sun"
//    )
//)

val publicPlaylist = listOf(
    PlaylistCategory(
        categoryTitle = "K-pop",
        list = listOf(
            Playlist(
                id = 1,
                name = "Playlist 1",
                imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=3160&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                cost = 30.00F,
                deliverDay = "Thu",
            ),
            Playlist(
                id = 2,
                name = "Playlist 2",
                imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=3160&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                cost = 50.00F,
                deliverDay = "Fri",
            ),
            Playlist(
                id = 3,
                name = "Playlist 3",
                imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=3160&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                cost = 15.90F,
                deliverDay = "Tue",
            )
        )
    ),
    PlaylistCategory(
        categoryTitle = "Summer’s Arrival",
        list = listOf(
            Playlist(
                id = 4,
                name = "Playlist 4",
                imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=3160&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                cost = 20.90F,
                deliverDay = "Wed",
            ),
            Playlist(
                id = 5,
                name = "Playlist 5",
                imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=3160&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                cost = 10.00F,
                deliverDay = "Sun",
            ),
            Playlist(
                id = 6,
                name = "Playlist 6",
                imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=3160&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                cost = 20.00F,
                deliverDay = "Sun",
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
        deliverDay = "Wed",
    ),
    Playlist(
        id = 8,
        name = "User Playlist 2",
        imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=3160&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        cost = 30.00F,
        deliverDay = "Fri",
    ),
    Playlist(
        id = 9,
        name = "User Playlist 3",
        imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=3160&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        cost = 70.00F,
        deliverDay = "Sun",
    ),
    Playlist(
        id = 10,
        name = "User Playlist 4",
        imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=3160&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        cost = 50.00F,
        deliverDay = "Sat",
    ),
)

val playlistList = AllPlaylist(publicPlaylist, userPlaylist)
