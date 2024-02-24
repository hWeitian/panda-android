package com.example.foodpanda_capstone.view.ui.composable

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdfScanner
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.foodpanda_capstone.R
import com.example.foodpanda_capstone.model.FoodItem
import com.example.foodpanda_capstone.model.RestaurantFoodItems
import com.example.foodpanda_capstone.view.ui.screen.EditableFoodItemContainer
import com.example.foodpanda_capstone.view.ui.screen.EditableFoodItemContent
import com.example.foodpanda_capstone.view.ui.screen.FoodItemContainer
import com.example.foodpanda_capstone.view.ui.theme.BrandDark
import com.example.foodpanda_capstone.view.ui.theme.BrandSecondary
import com.example.foodpanda_capstone.view.ui.theme.InteractionPrimary
import com.example.foodpanda_capstone.view.ui.theme.Typography

@Composable
fun FoodItemNameText(name: String) {
    Text(
        text = name,
        style = Typography.bodyLarge
    )
}


@Composable
fun FoodItemDescriptionText(description: String) {
    Text(
        text = description,
        style = Typography.bodyMedium,
        color = BrandSecondary
    )
}

@Composable
fun FoodItemRestaurantText(description: String) {
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            imageVector = Icons.Default.Storefront,
            contentDescription = "Storefront",
            tint = BrandSecondary,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.size(5.dp))
        Text(
            text = description,
            style = Typography.bodyMedium,
            color = BrandSecondary
        )
    }
}


@Composable
fun RestaurantNameText(name: String) {
    Text(
        text = name,
        style = Typography.titleSmall
    )
}


@Composable
fun RestaurantSection(
    restaurant: RestaurantFoodItems,
) {
    Column {
        RestaurantNameText(restaurant.restaurantName)
        restaurant.foodItems.map { item ->
            FoodItemContainer(item)
        }
    }
}


@Composable
fun FoodItemContainerCard(foodItemContent: @Composable () -> Unit) {
    Card(modifier = Modifier
        .drawBehind {
            drawLine(
                color = BrandSecondary,
                start = Offset(0f, size.height),
                end = Offset(size.width, size.height),
                strokeWidth = 1f,
            )
        }
        .padding(bottom = 10.dp, top = 18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(0.dp)
    ) {
        foodItemContent()
    }
}
