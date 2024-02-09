package com.example.foodpanda_capstone.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodpanda_capstone.model.FoodItem
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

    private val _searchResults = MutableStateFlow<List<FoodItem>>(emptyList())
    val searchResults: StateFlow<List<FoodItem>> = _searchResults

    private val _isInputOnFocus = MutableStateFlow(false)
    val isInputOnFocus: StateFlow<Boolean> = _isInputOnFocus

    fun updateIsInputOnFocusState(isFocus: Boolean) {
        _isInputOnFocus.value = isFocus
    }

    fun getRecentSearch(userId: Int = 1) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.fetchRecentSearch(userId).collect {
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

    fun clearSearchText() {
        _searchText.value = ""
    }

    fun getSearchResult() {
        viewModelScope.launch(Dispatchers.IO) {

            withContext(Dispatchers.Main) {
                _isLoading.value = true
                delay(1000)
            }
            try {
                repository.fetchSearchResults(searchText.value.toString()).collect { searchResult ->
                    _searchResults.value = updateSearchedDishQuantity(searchResult)
                }
            } catch (e: Exception) {
                logErrorMsg("getSearchResult", e)
            }

            withContext(Dispatchers.Main) {
                _isLoading.value = false
            }
        }
    }

    private fun updateSearchedDishQuantity(searchResults: List<FoodItem>): List<FoodItem> {

        val restaurantsInPlaylist = _currentPlaylist.value.foodItems

        var currentPlaylistDishIds = mutableMapOf<Int, Int>()

        if (restaurantsInPlaylist != null) {
            for (i in 0..restaurantsInPlaylist.size - 1) {
                val restaurantDishes = restaurantsInPlaylist[i]?.foodItems
                if (restaurantDishes != null) {
                    val restaurantDishesId = getCurrentPlaylistDishIds(restaurantDishes)
                    currentPlaylistDishIds = addMapToMap(currentPlaylistDishIds, restaurantDishesId)
                }
            }
        }

        val newResults = searchResults.map { dish ->
            if (currentPlaylistDishIds?.containsKey(dish.id) == true) {
                val quantity = currentPlaylistDishIds[dish.id]
                dish.copy(quantity = quantity!!)
            } else {
                dish.copy(quantity = 0)
            }

        }

        return newResults
    }

    private fun getCurrentPlaylistDishIds(playlistDishes: List<FoodItem>): Map<Int,Int> {
        var idMap = mutableMapOf<Int, Int>()
        for(i in 0..playlistDishes.size - 1){
            val dish = playlistDishes[i]
            dish.id?.let { idMap.put(it, dish.quantity) }
        }
        return idMap
    }

    private fun addMapToMap(
        currentPlaylistDishIds: MutableMap<Int, Int>,
        newPlaylistDishIds: Map<Int, Int>
    ): MutableMap<Int, Int> {
        newPlaylistDishIds.forEach { id ->
            currentPlaylistDishIds[id.key] = id.value
        }
        return currentPlaylistDishIds
    }

    fun clearSearchResult() {
        _searchResults.value = emptyList()
    }

    fun searchRecentSearchKeyword(keyword: String) {
        _searchText.value = keyword
        getSearchResult()
    }

    fun deleteRecentSearchKeyword(keyword: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.deleteRecentSearch(userId = 1, keyword = keyword)
                getRecentSearch()
            } catch (e: Exception) {
                logErrorMsg("deleteRecentSearchKeyword", e)
            }
        }
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
                    logErrorMsg("getOnePlaylist", e)
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
