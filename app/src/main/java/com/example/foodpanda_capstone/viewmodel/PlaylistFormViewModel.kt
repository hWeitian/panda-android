package com.example.foodpanda_capstone.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodpanda_capstone.model.Playlist
import com.example.foodpanda_capstone.model.PlaylistRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PlaylistFormViewModel (): ViewModel() {

    val cuisines: MutableState<String> =  mutableStateOf("")
    val numOfDish: MutableState<String> =  mutableStateOf("")
    val maxBudget: MutableState<String> =  mutableStateOf("")

}
