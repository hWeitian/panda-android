package com.example.foodpanda_capstone.view.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.foodpanda_capstone.model.RestaurantFoodItems
import com.example.foodpanda_capstone.view.ui.screen.FoodItemContainer
import com.example.foodpanda_capstone.view.ui.theme.BrandSecondary
import com.example.foodpanda_capstone.view.ui.theme.Typography

@Composable
fun FoodItemNameText(name: String){
    Text(
        text = name,
        style = Typography.bodyLarge
    )
}


@Composable
fun FoodItemDescriptionText(description: String){
    Text(
        text = description,
        style = Typography.bodyMedium,
        color = BrandSecondary
    )
}


@Composable
fun RestaurantNameText(name: String){
    Text(
        text = name,
        style = Typography.titleSmall
    )
}


@Composable
fun RestaurantSection(restaurant: RestaurantFoodItems) {
    Column {
        RestaurantNameText(restaurant.restaurantName)
        restaurant.foodItems.map { item ->
            FoodItemContainer(foodItem = item)
        }
    }
}
