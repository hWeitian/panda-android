package com.example.foodpanda_capstone.model


data class FoodItem(
    val name: String,
    val description: String,
    val quantity: Int,
    val price: Double,
    val imageUrl: String,
)

data class RestaurantFoodItems   (
    val restaurantName: String,
    val foodItems: List<FoodItem>
)
