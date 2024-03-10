package com.example.foodpanda_capstone.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodpanda_capstone.model.Days
import com.example.foodpanda_capstone.model.FinalPlaylist
import com.example.foodpanda_capstone.model.FoodItem
import com.example.foodpanda_capstone.model.Playlist
import com.example.foodpanda_capstone.model.PlaylistRepository
import com.example.foodpanda_capstone.model.RecentSearch
import com.example.foodpanda_capstone.model.RestaurantFoodItems
import com.example.foodpanda_capstone.utils.UserUtils
import com.example.foodpanda_capstone.utils.addMapToMap
import com.example.foodpanda_capstone.utils.getCurrentPlaylistDishIds
import com.example.foodpanda_capstone.utils.getCurrentPlaylistRestaurantNames
import com.example.foodpanda_capstone.utils.isCharFoundInText
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

    val cuisines: MutableState<String> = mutableStateOf("")
    val numOfDish: MutableState<String> = mutableStateOf("")
    val maxBudget: MutableState<String> = mutableStateOf("")

    private val _daysOfWeek = MutableStateFlow(
        listOf(
            Days("Mon", false),
            Days("Tue", false),
            Days("Wed", false),
            Days("Thu", false),
            Days("Fri", false),
            Days("Sat", false),
            Days("Sun", false),
        )
    )
    val daysOfWeek: StateFlow<List<Days>> = _daysOfWeek

    private val _selectedTimeOfDelivery = MutableLiveData("")
    val selectedTimeOfDelivery: LiveData<String> = _selectedTimeOfDelivery

    private val _canNavigate = MutableLiveData(false)
    val canNavigate: LiveData<Boolean> = _canNavigate


    fun onConfirmSubscriptionClick() {
        println("onConfirmSubscriptionClick")
        println("onConfirmSubscriptionClick: ${_daysOfWeek.value}")

        viewModelScope.launch(Dispatchers.IO) {

            withContext(Dispatchers.Main) {
                _isLoading.value = true
                delay(1000)
            }
            try {
                // TODO: Remove dummy user name
                val userId = "asdasdasdadsasdasdad"
//                val userId = getUserId()
                if (userId != null) {
                    val finalPlaylist = generateFinalPlaylistSubscriptionData(userId)
                    repository.subscribePlaylist(playlist = finalPlaylist, userId = userId)
                }
            } catch (e: Exception) {
                logErrorMsg("onConfirmSubscriptionClick", e)
            }

            withContext(Dispatchers.Main) {
                _canNavigate.value = true
                delay(3000)
                _isLoading.value = false
            }
        }
    }

    private fun getUserId(): String? {
        return UserUtils.getUserUID()
    }

    private fun generateFinalPlaylistSubscriptionData(userId: String): FinalPlaylist {
        return FinalPlaylist(
            id = _currentPlaylist.value.id,
            name = _currentPlaylist.value.name,
            imageUrl = _currentPlaylist.value.imageUrl,
            cost = _currentPlaylist.value.cost,
            deliveryDay = concatSelectDays(),
            foodItems = _currentPlaylist.value.foodItems,
            isPublic = false,
            deliverTime = _selectedTimeOfDelivery.value,
            userId = userId,
            status = "Subscribed"
        )
    }

    private fun concatSelectDays(): String {
        println("concatSelectDays: ${_daysOfWeek.value}")
        var selectedDaysString: String = ""
        _daysOfWeek.value.forEach { day ->
            if (day.isSelected) {
                selectedDaysString += "${day.name},"
            }
        }
        return selectedDaysString.trim { it == ',' }
    }


    fun updateSelectedTimeOfDelivery(inputText: String) {
        _selectedTimeOfDelivery.value = inputText
    }

    fun onDayClick(index: Int) {
        var newListOfDays = daysOfWeek.value.toMutableList()
        val newDay = Days(newListOfDays[index].name, !newListOfDays[index].isSelected)
        newListOfDays[index] = newDay
        _daysOfWeek.value = newListOfDays
    }

    private fun resetDaysOfWeek() {
        _daysOfWeek.value = createNewListOfDays()
    }

    private fun resetCanNavigate() {
        _canNavigate.value = false
    }

    fun resetData() {
        resetDaysOfWeek()
        resetCanNavigate()

    }

    private fun createNewListOfDays(): List<Days> {
        return listOf(
            Days("Mon", false),
            Days("Tue", false),
            Days("Wed", false),
            Days("Thu", false),
            Days("Fri", false),
            Days("Sat", false),
            Days("Sun", false),
        )
    }


    private fun updateSelectedDays(
        selectedDays: MutableList<Days>,
        nameOfDay: String
    ): MutableList<Days> {
        selectedDays.forEach { day ->
            if (day.name == nameOfDay) {
                println("name equals")
                day.isSelected = true
            }
        }
        println(selectedDays)
        return selectedDays
    }

    private fun updateSelectedDays(
        selectedDays: MutableList<Days>,
        selectedDaysMap: Map<String, String>
    ): MutableList<Days> {
        selectedDays.forEach { day ->
            val selectedDay = selectedDaysMap[day.name]
            if (selectedDay != null && day.name == selectedDay) {
                println("name equals")
                day.isSelected = true
            }
        }
        return selectedDays
    }

    private fun createCopyOfDaysOfWeek(daysOfWeek: List<Days>): MutableList<Days> {
        var newList = emptyList<Days>().toMutableList()
        for (day in _daysOfWeek.value) {
            newList.add(day.copy())
        }
        return newList
    }

    fun assignDaysOfWeek(selectedDays: String) {
        var newDaysOfWeek = _daysOfWeek.value.toMutableList()

        if (isCharFoundInText(",", selectedDays)) {
            val selectedDaysMap = emptyMap<String, String>().toMutableMap()
            selectedDays.split(",").forEach { day ->
                selectedDaysMap[day] = day
            }
            newDaysOfWeek = updateSelectedDays(newDaysOfWeek, selectedDaysMap)
        } else {
            newDaysOfWeek = updateSelectedDays(newDaysOfWeek, selectedDays)
        }
        _daysOfWeek.value = newDaysOfWeek.toList()
    }

    fun clearFormText() {
        cuisines.value = ""
        numOfDish.value = ""
        maxBudget.value = ""
    }


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
        val currentPlaylistDishIds = getDishesIdInCurrentPlaylist(restaurantsInPlaylist)

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

    private fun getDishesIdInCurrentPlaylist(restaurantsInPlaylist: List<RestaurantFoodItems?>?)
        : MutableMap<Int, Int> {
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

    fun onSearchResultDishAddBtnClicked(clickedDishId: Int, currentDishQuantity: Int, positionOfSearchResultDish: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val newDish = addQuantityOfDishInSearchResults(positionOfSearchResultDish, 1)
                updateSearchResults(newDish, positionOfSearchResultDish)

                if (currentDishQuantity > 0) {
                    onAddButtonClicked(clickedDishId)
                } else {
                    addDishToCurrentPlaylist(searchResults.value[positionOfSearchResultDish])
                }
            } catch (e: Exception) {
                logErrorMsg("onSearchResultDishAddBtnClicked", e)
            }
        }
    }

    fun onSearchResultDishMinusBtnClicked(
        clickedDishId: Int,
        positionOfSearchResultDish: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val updatedDish = minusQuantityOfDishInSearchResults(positionOfSearchResultDish, 1)
                updateSearchResults(updatedDish, positionOfSearchResultDish)
                onMinusButtonClicked(clickedDishId)
            } catch (e: Exception) {
                logErrorMsg("onSearchResultDishAddBtnClicked", e)
            }
        }
    }

    private suspend fun addDishToCurrentPlaylist(dishToAdd: FoodItem) {
        val restaurantNameOfDishToAdd = dishToAdd.restaurantName
        val dish = FoodItem(
            id = dishToAdd.id,
            name = dishToAdd.name,
            description = dishToAdd.description,
            quantity = dishToAdd.quantity,
            price = dishToAdd.price,
            imageUrl = dishToAdd.imageUrl,
            restaurantId = null,
            restaurantName = null
        )
        var currentPlaylistRestaurants = getCurrentPlaylistRestaurants()

        if (restaurantNameOfDishToAdd != null) {
            val currentRestaurantNames = getCurrentRestaurantNames()

            if (currentRestaurantNames.containsKey(restaurantNameOfDishToAdd)) {
                currentPlaylistRestaurants =
                    updateExistingRestaurant(
                        restaurantNameOfDishToAdd,
                        dish,
                        currentPlaylistRestaurants,
                        currentRestaurantNames
                    )
            } else {
                currentPlaylistRestaurants =
                    addNewRestaurant(restaurantNameOfDishToAdd, dish, currentPlaylistRestaurants)
            }

            val updatedCurrentPlaylistRestaurants = currentPlaylistRestaurants.toList()
            val updatedCost = calculateTotalPrice(updatedCurrentPlaylistRestaurants)
            updatePlaylist(updatedCurrentPlaylistRestaurants, updatedCost)
        } else {
            val e: Exception = Exception("restaurant name not found")
            logErrorMsg("addDishToCurrentPlaylist", e)
        }
    }

    private fun getCurrentPlaylistRestaurants(): MutableList<RestaurantFoodItems?> {
        return currentPlaylist.value.foodItems?.toMutableList() ?: mutableListOf()
    }

    private fun getCurrentRestaurantNames(): Map<String, Int> {
        val currentPlaylistRestaurants = getCurrentPlaylistRestaurants()
        return getCurrentPlaylistRestaurantNames(currentPlaylistRestaurants)
    }

    private fun updateExistingRestaurant(
        restaurantName: String,
        dish: FoodItem,
        currentPlaylistRestaurants: MutableList<RestaurantFoodItems?>,
        currentRestaurantNames: Map<String, Int>
    ): MutableList<RestaurantFoodItems?> {
        val positionOfRestaurant = currentRestaurantNames[restaurantName]
        if (positionOfRestaurant != null) {
            val restaurant = currentPlaylistRestaurants[positionOfRestaurant]
            val restaurantFoodItems = restaurant?.foodItems?.toMutableList()
            restaurantFoodItems?.add(dish)
            restaurant?.foodItems = restaurantFoodItems?.toList()!!
            currentPlaylistRestaurants[positionOfRestaurant] = restaurant
        }

        return currentPlaylistRestaurants
    }

    private fun addNewRestaurant(
        restaurantName: String,
        dish: FoodItem,
        currentPlaylistRestaurants: MutableList<RestaurantFoodItems?>
    ): MutableList<RestaurantFoodItems?> {
        val newRestaurantToAdd = RestaurantFoodItems(restaurantName = restaurantName, foodItems = listOf(dish))
        currentPlaylistRestaurants.add(newRestaurantToAdd)
        return currentPlaylistRestaurants
    }

    private fun addQuantityOfDishInSearchResults(dishIndex: Int, quantityToAdd: Int): FoodItem {
        return try {
            val currentDish = _searchResults.value[dishIndex]
            val newQuantity = currentDish.quantity + quantityToAdd

            currentDish.copy(quantity = newQuantity)
        } catch (e: Exception) {
            logErrorMsg("addQuantityInSearchResults", e)
            throw e
        }
    }

    private fun minusQuantityOfDishInSearchResults(dishIndex: Int, quantityToAdd: Int): FoodItem {
        return try {
            val currentDish = _searchResults.value[dishIndex]
            val newQuantity = currentDish.quantity - quantityToAdd

            currentDish.copy(quantity = newQuantity)
        } catch (e: Exception) {
            logErrorMsg("minusQuantityInSearchResults", e)
            throw e
        }
    }

    private suspend fun updateSearchResults(newDish: FoodItem, dishIndex: Int) {
        try {
            val updatedSearchResults = searchResults.value.toMutableList()
            updatedSearchResults[dishIndex] = newDish

            _searchResults.emit(updatedSearchResults)
        } catch (e: Exception) {
            logErrorMsg("updateSearchResults", e)
            throw e
        }
    }

    fun getRandomPlaylist() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                withContext(Dispatchers.Main) {
                    _isLoading.value = true
                    delay(1000)
                }
                val numOfDishInt = Integer.parseInt(numOfDish.value)
                val maxBudgetInt = Integer.parseInt(maxBudget.value)
                repository
                    .fetchRandomPlaylist(cuisines.value, numOfDishInt, maxBudgetInt)
                    .collect { playlist ->
                        _currentPlaylist.value = playlist
                        println(playlist)
                    }
                withContext(Dispatchers.Main) {
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                logErrorMsg("getRandomPlaylist", e)
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
                        playlist.deliveryDay?.let { assignDaysOfWeek(it) }
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
