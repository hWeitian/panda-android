package com.example.foodpanda_capstone.view.ui.screen

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.foodpanda_capstone.R
import com.example.foodpanda_capstone.model.FoodItem
import com.example.foodpanda_capstone.view.ui.composable.BoxIconButton
import com.example.foodpanda_capstone.view.ui.composable.FoodItemContainerCard
import com.example.foodpanda_capstone.view.ui.composable.FoodItemDescriptionText
import com.example.foodpanda_capstone.view.ui.composable.FoodItemNameText
import com.example.foodpanda_capstone.view.ui.composable.ImageHolder
import com.example.foodpanda_capstone.view.ui.composable.PrimaryButton
import com.example.foodpanda_capstone.view.ui.composable.RestaurantSection
import com.example.foodpanda_capstone.view.ui.theme.InteractionPrimary
import com.example.foodpanda_capstone.view.ui.theme.Typography
import com.example.foodpanda_capstone.viewmodel.PlaylistViewModel

@Composable
fun EditPlaylistScreen(navController: NavController, viewModel: PlaylistViewModel) {
    // TODO: Add search bar

    val currentPlaylist by viewModel.currentPlaylist.collectAsState()
    val totalAmountCardHeight: Int = 130
    val endOfPageSpace: Int = totalAmountCardHeight + 30

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
                Column {
                    it.foodItems?.map { restaurantFoodItems ->
                        Spacer(modifier = Modifier.size(20.dp))
                        RestaurantSection(restaurantFoodItems, true)
                    }
                    Spacer(modifier = Modifier.size(endOfPageSpace.dp))

                }
            }
        }
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 12.dp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(totalAmountCardHeight.dp)
                .align(Alignment.BottomCenter)
                .layout() { measurable, constraints ->
                    val placeable = measurable.measure(
                        constraints.copy(
                            maxWidth = constraints.maxWidth + 30.dp.roundToPx(), // add padding to offset parent's padding
                        )
                    )
                    layout(placeable.width, placeable.height) {
                        placeable.place(0, 0)
                    }
                },
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(15.dp),
            ) {
                Row(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Total Cost", style = Typography.titleMedium)
                    Text(
                        text = "S$ ${"%.2f".format(currentPlaylist?.cost)}",
                        style = Typography.titleMedium
                    )
                }
                Spacer(modifier = Modifier.size(20.dp))
                PrimaryButton(name = "Subscribed", width = null) {
                    // navController.navigate("EditPlaylist")  TODO: Add navigate to confirmation page
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
        Modifier
            .fillMaxWidth()
            .height(82.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        EditQuantityButtons(foodItem.quantity)
        Column(
            Modifier
                .width(220.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                FoodItemNameText(foodItem.name)
                FoodItemDescriptionText(foodItem.description)
            }
            Text(
                text = "S$ ${"%.2f".format(foodItem.price)}",
                style = Typography.headlineLarge,
                modifier = Modifier.padding(top = 5.dp)
            )
        }
        Spacer(modifier = Modifier.size(15.dp))
        ImageHolder(imageUrl = foodItem.imageUrl, height = 70, description = foodItem.name)
    }
}

@Composable
fun EditQuantityButtons(quantity: Int) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(end = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        BoxIconButton({
            Icon(
                painter = painterResource(id = R.drawable.baseline_add_24_pink),
                contentDescription = "Add Button",
                tint = InteractionPrimary,
            )
        }, { Log.i("Panda", "Add btn clicked") }) // TODO: Add clicked logic

        Card(
            modifier = Modifier
                .width(25.dp)
                .height(25.dp),
            shape = RoundedCornerShape(5.dp),
            border = BorderStroke(1.dp, InteractionPrimary),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = quantity.toString(), style = Typography.bodyMedium)
            }
        }
        BoxIconButton({
            Icon(
                painter = painterResource(id = R.drawable.baseline_remove_24_pink),
                contentDescription = "Remove Button",
                tint = InteractionPrimary,
            )
        }, { Log.i("Panda", "Remove btn clicked") }) // TODO: Add clicked logic
    }
}
