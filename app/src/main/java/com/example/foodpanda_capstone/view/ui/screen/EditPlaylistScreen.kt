package com.example.foodpanda_capstone.view.ui.screen

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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.foodpanda_capstone.R
import com.example.foodpanda_capstone.model.FoodItem
import com.example.foodpanda_capstone.view.ui.composable.BoxIconButton
import com.example.foodpanda_capstone.view.ui.composable.FoodItemContainerCard
import com.example.foodpanda_capstone.view.ui.composable.FoodItemDescriptionText
import com.example.foodpanda_capstone.view.ui.composable.FoodItemNameText
import com.example.foodpanda_capstone.view.ui.composable.FoodItemRestaurantText
import com.example.foodpanda_capstone.view.ui.composable.ImageHolder
import com.example.foodpanda_capstone.view.ui.composable.PrimaryButton
import com.example.foodpanda_capstone.view.ui.composable.RestaurantNameText
import com.example.foodpanda_capstone.view.ui.theme.InteractionPrimary
import com.example.foodpanda_capstone.view.ui.theme.Typography
import com.example.foodpanda_capstone.viewmodel.PlaylistViewModel

@Composable
fun EditPlaylistScreen(navController: NavController, viewModel: PlaylistViewModel) {

    val currentPlaylist by viewModel.currentPlaylist.collectAsState()
    val totalAmountCardHeight: Int = 130
    val endOfPageSpace: Int = totalAmountCardHeight + 30

    val offsetPadding = dimensionResource(R.dimen.base_side_padding) * 2

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
            Spacer(modifier = Modifier.size(10.dp))
            SearchInput(
                isInputOnFocus = null,
                isEnabled = false,
                isClickable = false,
                placeholderText = "Search for food",
                inputValue = "",
                onSearch = null,
                updateInput = {},
                updateIsInputOnFocus = {},
            ) { navController.navigate("Search") }
            currentPlaylist?.let {
                Column {
                    it.foodItems?.map { restaurantFoodItems ->
                        Spacer(modifier = Modifier.size(20.dp))
                        Column {
                            if (restaurantFoodItems != null) {
                                RestaurantNameText(restaurantFoodItems.restaurantName)
                            }
                            restaurantFoodItems?.foodItems?.map { item ->
                                EditableFoodItemContainer(item,
                                    { viewModel.onAddButtonClicked(item.id) },
                                    { viewModel.onMinusButtonClicked(item.id) }
                                )
                            }
                        }
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
                            maxWidth = constraints.maxWidth + offsetPadding.roundToPx(), // add padding to offset parent's padding
                        )
                    )
                    layout(placeable.width, placeable.height) {
                        placeable.place(0, 0)
                    }
                },
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(dimensionResource(R.dimen.base_side_padding)),
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

                PrimaryButton(
                    name = if (currentPlaylist?.isPublic == true) "Subscribe" else "Update",
                    width = null
                ) {
                    navController.navigate("Playlist Confirm")
                }
            }
        }
    }

}


@Composable
fun EditableFoodItemContainer(
    foodItem: FoodItem,
    addQuantity: () -> Unit,
    reduceQuantity: () -> Unit
) {
    FoodItemContainerCard {
        EditableFoodItemContent(foodItem, addQuantity, reduceQuantity)
    }
}

@Composable
fun EditableFoodItemContent(
    foodItem: FoodItem,
    addQuantity: () -> Unit,
    reduceQuantity: () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(if (foodItem.restaurantName != null) 100.dp else 82.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        EditQuantityButtons(
            foodItem.quantity,
            addQuantity,
            reduceQuantity
        )
        Column(
            Modifier
                .width(220.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                FoodItemNameText(foodItem.name)
                FoodItemDescriptionText(foodItem.description)

                if (foodItem.restaurantName != null) {
                    Spacer(modifier = Modifier.size(10.dp))
                    FoodItemRestaurantText(foodItem.restaurantName)
                }
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
fun EditQuantityButtons(
    quantity: Int,
    addQuantity: () -> Unit,
    reduceQuantity: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(end = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        BoxIconButton(
            isEnabled = true,
            {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_add_24_pink),
                    contentDescription = "Add Button",
                    tint = InteractionPrimary,
                )
            }, { addQuantity() })

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
        BoxIconButton(
            isEnabled = quantity > 0,
            {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_remove_24_pink),
                    contentDescription = "Remove Button",
                    tint = InteractionPrimary,
                )
            }, { reduceQuantity() })
    }
}
