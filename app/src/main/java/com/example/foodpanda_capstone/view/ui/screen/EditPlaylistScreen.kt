package com.example.foodpanda_capstone.view.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.foodpanda_capstone.model.FoodItem
import com.example.foodpanda_capstone.view.ui.composable.FoodItemContainerCard
import com.example.foodpanda_capstone.view.ui.composable.FoodItemDescriptionText
import com.example.foodpanda_capstone.view.ui.composable.FoodItemNameText
import com.example.foodpanda_capstone.view.ui.composable.ImageHolder
import com.example.foodpanda_capstone.view.ui.composable.RestaurantSection
import com.example.foodpanda_capstone.view.ui.theme.BrandDark
import com.example.foodpanda_capstone.view.ui.theme.BrandSecondary
import com.example.foodpanda_capstone.view.ui.theme.Typography
import com.example.foodpanda_capstone.viewmodel.PlaylistViewModel

@Composable
fun EditPlaylistScreen(navController: NavController, viewModel: PlaylistViewModel) {
    // TODO: Add search bar

    val currentPlaylist by viewModel.currentPlaylist.collectAsState()

    Log.i("WT", currentPlaylist.toString())

//    Text(text = "Ehllo")

    Box(
        modifier = Modifier
            .padding(top = 5.dp)
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            currentPlaylist?.let {
                Column(Modifier.padding(top = 10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = it.name,
                            style = Typography.titleMedium
                        )
                        Text(
                            text = "S$ ${"%.2f".format(it.cost)}",
                            style = Typography.titleMedium
                        )
                    }
                    it.foodItems?.map { restaurantFoodItems ->
                        Spacer(modifier = Modifier.size(20.dp))
                        RestaurantSection(restaurantFoodItems, true)
                    }


                }
            }
        }

    }

}


@Composable
fun EditableFoodItemContainer(foodItem: FoodItem) {
    FoodItemContainerCard { EditableFoodItemContent(foodItem) }
}

@Composable
fun EditableFoodItemContent(foodItem: FoodItem) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            Modifier
                .width(250.dp)
                .height(80.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                FoodItemNameText(foodItem.name)
                FoodItemDescriptionText(foodItem.description)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp),
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
        Spacer(modifier = Modifier.size(15.dp))
        ImageHolder(imageUrl = foodItem.imageUrl, height = 80, description = foodItem.name)
    }
}
