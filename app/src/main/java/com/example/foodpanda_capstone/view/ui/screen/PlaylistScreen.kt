package com.example.foodpanda_capstone.view.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import com.example.foodpanda_capstone.view.ui.composable.*
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
    val canNavigate by viewModel.canNavigate.observeAsState()

    LaunchedEffect(id) {
        if (id != null) {
            viewModel.getOnePlaylist(id)
        }
    }

    LaunchedEffect(canNavigate) {
        if (canNavigate == true) {
            navController.navigate("Playlist List")
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.resetData()
        }
    }

    Box(
        modifier = Modifier
            .padding(top = 5.dp)
            .fillMaxSize()
            .background(Color.White)
    ) {
        if (!isLoading) {
            currentPlaylist?.let {
                LazyColumn(Modifier.padding(top = 10.dp)) {
                    item {
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
                    }

                    items(it.foodItems.orEmpty()) { restaurantFoodItems ->
                        if (restaurantFoodItems != null) {
                            Spacer(modifier = Modifier.height(20.dp))
                            RestaurantNameText(restaurantFoodItems.restaurantName)
                            restaurantFoodItems.foodItems.map { item ->
                                FoodItemContainer(item)
                            }
                        }
                    }

                    item {
                        when {
                            it.id == 0 -> RandomPlaylistButtons(
                                navController = navController,
                                playlistName = it.name,
                                viewModel = viewModel
                            )

                            it.isPublic == true -> PublicPlaylistButtons(
                                navController = navController,
                                playlistName = it.name,
                                viewModel = viewModel
                            )

                            else -> PrivatePlayListButtons(
                                openModal = openModal,
                                navController = navController,
                                playlistName = it.name
                            )
                        }
                        ScreenBottomSpacer()
                    }

                }
            }
        } else {
            LoadingScreen()
        }

        if (openModal.value) {
            Modal (
                title = "Cancel Subscription",
                description = "Cancellation of playlist subscription will start next week.\r\n\nAre you sure you want to cancel?",
                buttonTitle = "Confirm",
                onDismissRequest = { openModal.value = false },
                onConfirmation = {
                    viewModel.onConfirmCancelSubscriptionClick()
                    openModal.value = false
                }
            )
        }

    }
}

@Composable
fun FoodItemContainer(foodItem: FoodItem) {
    FoodItemContainerCard { FoodItemContent(foodItem) }
}

@Composable
fun RandomPlaylistButtons(navController: NavController, playlistName: String, viewModel: PlaylistViewModel) {
    CustomTextBtn(
        name = "Edit Playlist",
        iconVector = null,
        iconImgId = R.drawable.baseline_edit_24_white) { navController.navigate("EditPlaylist/Editing $playlistName")}

    Spacer(modifier = Modifier.size(40.dp))

    CustomOutlinedBtn(name = "Not Happy? Regenerate", width = null) {
        viewModel.getRandomPlaylist()
    }

    Spacer(modifier = Modifier.size(10.dp))

    PrimaryButton(name = "Subscribe", width = null) {
        navController.navigate("Playlist Confirm")
    }
}

@Composable
fun PublicPlaylistButtons(navController: NavController, playlistName: String, viewModel: PlaylistViewModel) {
    CustomTextBtn(
        name = "Edit Playlist",
        iconVector = null,
        iconImgId = R.drawable.baseline_edit_24_white) { navController.navigate("EditPlaylist/Editing $playlistName")}

    Spacer(modifier = Modifier.size(40.dp))

    PrimaryButton(name = "Subscribe", width = null) {
        navController.navigate("Playlist Confirm")
    }
}

@Composable
fun PrivatePlayListButtons(openModal: MutableState<Boolean>, navController: NavController, playlistName: String) {
    CustomTextBtn(
        name = "Cancel Subscription",
        iconVector = null,
        iconImgId = R.drawable.baseline_cancel_24_white) { openModal.value = true }

    Spacer(modifier = Modifier.size(40.dp))

    PrimaryButton(name = "Edit", width = null) {
        navController.navigate("EditPlaylist/Editing $playlistName")
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
        ImageHolder(imageUrl = foodItem.imageUrl, height = 90, description = foodItem.name)
    }
}
