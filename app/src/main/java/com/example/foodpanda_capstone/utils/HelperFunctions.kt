package com.example.foodpanda_capstone.utils

import android.util.Log
import com.example.foodpanda_capstone.model.FoodItem
import com.example.foodpanda_capstone.model.RestaurantFoodItems

fun getCurrentPlaylistDishIds(playlistDishes: List<FoodItem>): Map<Int,Int> {
    var idMap = mutableMapOf<Int, Int>()
    for(i in 0..playlistDishes.size - 1){
        val dish = playlistDishes[i]
        dish.id?.let { idMap.put(it, dish.quantity) }
    }
    return idMap
}

fun getCurrentPlaylistRestaurantNames(currentPlaylistRestaurants: MutableList<RestaurantFoodItems?>): Map<String,Int> {
    var idMap = mutableMapOf<String, Int>()
    for(i in 0..currentPlaylistRestaurants.size - 1){
        val restaurant = currentPlaylistRestaurants[i]
        restaurant?.restaurantName?.let { idMap.put(it, i) }
    }
    return idMap
}

fun addMapToMap(
    currentPlaylistDishIds: MutableMap<Int, Int>,
    newPlaylistDishIds: Map<Int, Int>
): MutableMap<Int, Int> {
    newPlaylistDishIds.forEach { id ->
        currentPlaylistDishIds[id.key] = id.value
    }
    return currentPlaylistDishIds
}

fun isCharFoundInText(char: String, text: String): Boolean {
    return text.contains(char)
}
