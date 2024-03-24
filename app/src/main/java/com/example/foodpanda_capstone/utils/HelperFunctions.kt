package com.example.foodpanda_capstone.utils

import android.util.Log
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.text.toUpperCase
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

fun logErrorMsg(functionName: String, error: Exception){
    Log.e("PdError", "Error at $functionName - $error")
}

fun splitDaysToMap(days: String): Map<String, String> {
    val daysMap = emptyMap<String, String>().toMutableMap()
    days.split(",").forEach { day ->
        daysMap[day] = day
    }
    return daysMap
}

fun splitDaysToList(days: String): List<String> {
    val daysList = mutableListOf<String>()
    days.split(",").forEach { day ->
        day.trim().trim { it == ',' }
        daysList.add(day)
    }
    return daysList
}

object DaysMap {
    val map = mapOf(
        "Mon" to "Monday",
        "Tue" to "Tuesday",
        "Wed" to "Wednesday",
        "Thu" to "Thursday",
        "Fri" to "Friday",
        "Sat" to "Saturday",
        "Sun" to "Sunday"
    )
}

fun Modifier.grayScale(): Modifier {
    val saturationMatrix = ColorMatrix().apply { setToSaturation(0f) }
    val saturationFilter = ColorFilter.colorMatrix(saturationMatrix)
    val paint = Paint().apply { colorFilter = saturationFilter }

    return drawWithCache {
        val canvasBounds = Rect(Offset.Zero, size)
        onDrawWithContent {
            drawIntoCanvas {
                it.saveLayer(canvasBounds, paint)
                drawContent()
                it.restore()
            }
        }
    }
}

fun String.truncateString(): String {
    var finalString = ""
    if (this.length > 9) {
        finalString = this.trim().slice(0..9)
        finalString += "... "
    } else {
        finalString = this
    }
    return finalString
}

