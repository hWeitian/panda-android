package com.example.foodpanda_capstone.view.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.unit.dp
import com.example.foodpanda_capstone.model.FoodItem
import com.example.foodpanda_capstone.view.ui.theme.BrandDark
import com.example.foodpanda_capstone.view.ui.theme.BrandSecondary
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
    Row(
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
fun FoodItemContainerWithoutImage(foodItem: FoodItem, restaurantName: String) {
    FoodItemContainerCard { FoodItemContentWithoutImage(foodItem, restaurantName) }
}

@Composable
fun FoodItemContainer(foodItem: FoodItem) {
    FoodItemContainerCard { FoodItemContent(foodItem) }
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
        .padding(bottom = 10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(0.dp)
    ) {
        foodItemContent()
    }
}

@Composable
fun FoodItemContent(foodItem: FoodItem) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            Modifier
                .weight(0.7f)
                .height(80.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                FoodItemNameText(foodItem.name)
                FoodItemDescriptionText(foodItem.description)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                Text(
                    text = "Qty: ${foodItem.quantity}",
                    style = Typography.bodyMedium,
                    color = BrandDark
                )
                Text(
                    text = "S$ ${"%.2f".format(foodItem.price)}",
                    style = Typography.bodyMedium
                )
            }
        }
        Row(
            modifier = Modifier
                .weight(0.3f)
                .fillMaxWidth()
        ) {
            Spacer(Modifier.weight(0.2f))
            Box(modifier = Modifier.weight(0.8f)) {
                ImageHolder(imageUrl = foodItem.imageUrl, height = 90, description = foodItem.name)
            }
        }
    }
}

@Composable
fun FoodItemContentWithoutImage(foodItem: FoodItem, restaurantName: String) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            Modifier
                .weight(0.7f)
        ) {
            Column {
                Text(
                    text = foodItem.name,
                    style = Typography.headlineLarge,
                )
                Spacer(modifier = Modifier.size(2.dp))
                FoodItemDescriptionText(foodItem.description)
                Spacer(modifier = Modifier.size(4.dp))
                FoodItemRestaurantText(restaurantName)
                Spacer(modifier = Modifier.size(5.dp))
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
            )
            {
                Text(
                    text = "Qty: ${foodItem.quantity}",
                    style = Typography.bodyMedium,
                    color = BrandDark
                )
            }
        }
        Text(
            text = "S$ ${"%.2f".format(foodItem.price)}",
            style = Typography.headlineLarge,
        )
    }
}
