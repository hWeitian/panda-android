package com.example.foodpanda_capstone.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel

class PlaylistFormViewModel: ViewModel() {
    val cuisines: MutableState<String> =  mutableStateOf("")
    val numOfDish: MutableState<String> =  mutableStateOf("")
    val maxBudget: MutableState<String> =  mutableStateOf("")
}
