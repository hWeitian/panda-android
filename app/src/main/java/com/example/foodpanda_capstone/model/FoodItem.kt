package com.example.foodpanda_capstone.model


import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class FoodItem(
    @SerializedName("dishId")
    val id: Int,
    val name: String,
    val description: String,
    var quantity: Int,
    val price: BigDecimal,
    val imageUrl: String,
    val restaurantId: Int?,
    val restaurantName: String?
)

data class RestaurantFoodItems   (
    val restaurantName: String,
    val foodItems: List<FoodItem>
)
