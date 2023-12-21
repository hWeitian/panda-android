package com.example.foodpanda_capstone.view.ui.screen

import android.util.Log
import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
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
import com.example.foodpanda_capstone.view.ui.composable.RestaurantSection
import com.example.foodpanda_capstone.view.ui.composable.bounceClick
import com.example.foodpanda_capstone.view.ui.theme.BrandDark
import com.example.foodpanda_capstone.view.ui.theme.BrandSecondary
import com.example.foodpanda_capstone.view.ui.theme.InteractionPrimary
import com.example.foodpanda_capstone.view.ui.theme.Typography
import com.example.foodpanda_capstone.viewmodel.PlaylistViewModel

@Composable
fun EditPlaylistScreen(navController: NavController, viewModel: PlaylistViewModel) {
    // TODO: Add search bar

    val currentPlaylist by viewModel.currentPlaylist.collectAsState()

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
        Modifier
            .fillMaxWidth()
            .height(90.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        EditQuantityButtons(20) // TODO: Change to dynamic quantity
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
                style = Typography.bodyMedium, fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 5.dp)
            )
        }
        Spacer(modifier = Modifier.size(15.dp))
        ImageHolder(imageUrl = foodItem.imageUrl, height = 90, description = foodItem.name)
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
