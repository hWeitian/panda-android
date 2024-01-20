package com.example.foodpanda_capstone.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodpanda_capstone.model.PlaylistRepository

class SearchViewModel(private val repository: PlaylistRepository): ViewModel() {
    private val _searchText = MutableLiveData("")
    val searchText: LiveData<String> = _searchText

    fun updateSearchText(inputText: String) {
        _searchText.value = inputText
    }

    fun search() {
        Log.i("WT", "Keyboard Search Clicked: ${searchText.value.toString()}")
    }
}
