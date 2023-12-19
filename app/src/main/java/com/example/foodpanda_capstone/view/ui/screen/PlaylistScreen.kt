package com.example.foodpanda_capstone.view.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.foodpanda_capstone.R
import com.example.foodpanda_capstone.model.FoodItem
import com.example.foodpanda_capstone.model.Playlist
import com.example.foodpanda_capstone.model.PlaylistRepository
import com.example.foodpanda_capstone.model.RestaurantFoodItems
import com.example.foodpanda_capstone.view.ui.composable.CustomOutlinedBtn
import com.example.foodpanda_capstone.view.ui.composable.CustomTextBtn
import com.example.foodpanda_capstone.view.ui.composable.FoodItemDescriptionText
import com.example.foodpanda_capstone.view.ui.composable.FoodItemNameText
import com.example.foodpanda_capstone.view.ui.composable.ImageHolder
import com.example.foodpanda_capstone.view.ui.composable.Modal
import com.example.foodpanda_capstone.view.ui.composable.PrimaryButton
import com.example.foodpanda_capstone.view.ui.composable.RestaurantNameText
import com.example.foodpanda_capstone.view.ui.composable.RestaurantSection
import com.example.foodpanda_capstone.view.ui.theme.BrandDark
import com.example.foodpanda_capstone.view.ui.theme.BrandPrimary
import com.example.foodpanda_capstone.view.ui.theme.BrandSecondary
import com.example.foodpanda_capstone.view.ui.theme.Typography
import com.example.foodpanda_capstone.viewmodel.GeneralViewModelFactory
import com.example.foodpanda_capstone.viewmodel.PlaylistViewModel
import kotlinx.coroutines.runBlocking

@Composable
fun PlaylistScreen(navController: NavController, id: Int?, viewModel: PlaylistViewModel) {

    val openModal = remember { mutableStateOf(false) }

    val currentPlaylist by viewModel.currentPlaylist.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(id) {
        if (id != null) {
            viewModel.getOnePlaylist(id)
        }
    }

    Box(
        modifier = Modifier
            .padding(top = 5.dp)
            .fillMaxSize()
            .background(Color.White)
    ) {
        if (!isLoading) {
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
                            RestaurantSection(restaurantFoodItems)
                        }

                        if(it.isPublic == true){
                            PublicPlaylistButtons()
                        } else {
                            PrivatePlayListButtons(openModal, navController)
                        }
                    }
                }
            }

        } else {
            Text(text = "Loading...")
        }

        if(openModal.value){
            // TODO: Logic to cancel subscription
            Modal (
                title = "Cancel Subscription",
                description = "Cancellation of playlist subscription will start next week.\r\n\nAre you sure you want to cancel?",
                buttonTitle = "Confirm",
                onDismissRequest = { openModal.value = false},
                onConfirmation = { Log.i("Panda", "Cancel Subscription button clicked.") } // TODO: Add cancel subscription logic}
            )
        }

    }
}

@Composable
fun FoodItemContainer(foodItem: FoodItem) {
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

}

@Composable
fun PublicPlaylistButtons() {
    CustomTextBtn(
        name = "Edit Playlist",
        iconVector = null,
        iconImgId = R.drawable.baseline_edit_24_white) { Log.i("Panda", "Edit playlist btn clicked")} // TODO: Link to edit playlist page

    Spacer(modifier = Modifier.size(40.dp))

    CustomOutlinedBtn(name = "Not Happy? Regenerate", width = null) {
        Log.i("Panda", "Regenerate playlist btn clicked") // TODO: Add regenerate playlist logic
    }

    Spacer(modifier = Modifier.size(10.dp))

    PrimaryButton(name = "Subscribe", width = null) {
        Log.i("Panda", "Subscribe playlist btn clicked") // TODO: Link to subscribe playlist logic
    }
}

@Composable
fun PrivatePlayListButtons(openModal: MutableState<Boolean>, navController: NavController) {
    CustomTextBtn(
        name = "Cancel Subscription",
        iconVector = null,
        iconImgId = R.drawable.baseline_cancel_24_white) { openModal.value = true }

    Spacer(modifier = Modifier.size(40.dp))

    PrimaryButton(name = "Edit", width = null) {
        navController.navigate("EditPlaylist")
    }
}
