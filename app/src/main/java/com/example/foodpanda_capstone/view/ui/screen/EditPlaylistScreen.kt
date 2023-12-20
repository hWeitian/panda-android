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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.foodpanda_capstone.view.ui.composable.RestaurantSection
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
                            RestaurantSection(restaurantFoodItems)
                        }


                    }
                }
            }

        }

}
