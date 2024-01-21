package com.example.foodpanda_capstone.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodpanda_capstone.model.Playlist
import com.example.foodpanda_capstone.model.PlaylistRepository
import com.example.foodpanda_capstone.model.RecentSearch
import com.example.foodpanda_capstone.model.RestaurantFoodItems
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigDecimal

class PlaylistViewModel(private val repository: PlaylistRepository) : ViewModel() {

    private val _currentPlaylist = MutableStateFlow(
        Playlist(
            id = 0,
            name = "",
            imageUrl = "",
            cost = BigDecimal(0),
            deliveryDay = "",
            foodItems = emptyList(),
            isPublic = false
        )
    )
    val currentPlaylist: StateFlow<Playlist> = _currentPlaylist.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _searchText = MutableLiveData("")
    val searchText: LiveData<String> = _searchText

    private val _recentSearch = MutableStateFlow<List<RecentSearch>>(emptyList())
    val recentSearch: StateFlow<List<RecentSearch>> = _recentSearch

    fun getRecentSearch() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.fetchRecentSearch(1).collect {
                    _recentSearch.value = it
                }
            } catch (e: Exception) {
                logErrorMsg("getRecentSearch", e)
            }
        }
    }

    fun updateSearchText(inputText: String) {
        _searchText.value = inputText
    }

    fun search() {
        Log.i("WT", "Keyboard Search Clicked: ${searchText.value.toString()}")
    }

    fun getOnePlaylist(playlistId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            while (currentPlaylist.value.name.isBlank() || currentPlaylist.value.id != playlistId) {
                withContext(Dispatchers.Main) {
                    _isLoading.value = true
                    delay(1000)
                }
                try {
                    repository.fetchOnePlaylist(playlistId).collect { playlist ->
                        _currentPlaylist.value = playlist
                    }
                } catch (e: Exception) {
                    logErrorMsg("getAllPlaylist", e)
                }
                if (currentPlaylist.value.name.isNotBlank() || currentPlaylist.value.id == playlistId) {
                    withContext(Dispatchers.Main) {
                        _isLoading.value = false
                    }
                }
            }
        }
    }

    fun onAddButtonClicked(dishId: Int?) {
        viewModelScope.launch(Dispatchers.IO) {
            val playlist = currentPlaylist.value
            if (playlist.name.isNotBlank()) {
                val updatedRestaurantList = addQuantity(dishId, 1, playlist)
                val updatedCost = calculateTotalPrice(updatedRestaurantList)
                updatePlaylist(updatedRestaurantList, updatedCost)
            }
        }
    }

    fun onMinusButtonClicked(dishId: Int?) {
        viewModelScope.launch(Dispatchers.IO) {
            val playlist = currentPlaylist.value
            if (playlist.name.isNotBlank()) {
                val updatedRestaurantList = reduceQuantity(dishId, 1, playlist)
                val updatedCost = calculateTotalPrice(updatedRestaurantList)
                updatePlaylist(updatedRestaurantList, updatedCost)
            }
        }
    }

    private suspend fun updatePlaylist(updatedRestaurantList: List<RestaurantFoodItems?>, updatedCost: BigDecimal) {
        val playlist = currentPlaylist.value
        playlist.copy(foodItems = updatedRestaurantList, cost = updatedCost).let { _currentPlaylist.emit(it) }
    }

    private fun addQuantity(dishId: Int?, amount: Int, playlist: Playlist): List<RestaurantFoodItems> {
        val updatedFoodList = playlist.foodItems!!.map { res ->
            val foodItems = res!!.foodItems
            foodItems.let { items ->
                val updatedItems = items.map { food ->
                    // If it's the food we are looking for,
                    // increase the quantity
                    if (food.id == dishId) {
                        food.copy(quantity = food.quantity + amount)
                    } else {
                        food
                    }
                }
                // Create a new restaurant with updated food items
                res.copy(foodItems = updatedItems)
            }
        }
        return updatedFoodList
    }

    private fun reduceQuantity(dishId: Int?, amount: Int, playlist: Playlist): List<RestaurantFoodItems?> {
        val updatedFoodList = playlist.foodItems!!.map { restaurant ->
            restaurant?.let { res ->
                val foodItems = res.foodItems
                foodItems.let { items ->
                    val updatedItems = items.mapNotNull { food ->
                        // If it's the food we are looking for and quantity is more than 1,
                        // decrease the quantity
                        if (food.id == dishId && food.quantity > 1) {
                            food.copy(quantity = food.quantity - amount)
                        } else if (food.id !== dishId) {
                            food
                        } else {
                            // Do not include the item if foodId == dishId
                            // and quantity is 1
                            null // Do nothing
                        }
                    }
                    // Create a new restaurant with updated food items
                    res.copy(foodItems = updatedItems)
                }
            }
        }
            .filterNotNull()
            .filter { restaurant -> restaurant.foodItems.isNotEmpty() }
            .toMutableList()

        return updatedFoodList
    }

    private fun calculateTotalPrice(restaurantFoodItems: List<RestaurantFoodItems?>): BigDecimal {
        var totalCost = BigDecimal(0)
        restaurantFoodItems.forEach { restaurant ->
            val foodItems = restaurant?.foodItems
            foodItems?.forEach { food ->
                totalCost += BigDecimal(food.quantity) * food.price
            }
        }
        return totalCost
    }


    override fun onCleared() {
        super.onCleared()

        // Here, you can write the logic for what should be done when ViewModel is cleared.
        // For example you can log it:

        Log.d("WT", "Playlist ViewModel has been cleared.")
    }


}
