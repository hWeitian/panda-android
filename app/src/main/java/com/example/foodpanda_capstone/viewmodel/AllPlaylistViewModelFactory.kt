package com.example.foodpanda_capstone.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodpanda_capstone.model.PlaylistRepository
import java.lang.IllegalArgumentException

class AllPlaylistViewModelFactory(private val repository: PlaylistRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AllPlaylistViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return AllPlaylistViewModel(repository) as T
        }
        throw IllegalArgumentException("AllPlaylistViewModel Class not found")
    }
}
