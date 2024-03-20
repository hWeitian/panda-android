package com.example.foodpanda_capstone.view.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.foodpanda_capstone.view.ui.composable.CustomNumberInputField
import com.example.foodpanda_capstone.view.ui.composable.CustomTextInputField
import com.example.foodpanda_capstone.view.ui.composable.PrimaryButton
import com.example.foodpanda_capstone.viewmodel.PlaylistViewModel

@Composable
fun PlaylistFormScreen(navController: NavController, viewModel: PlaylistViewModel) {

    LaunchedEffect(Unit) {
        viewModel.clearFormText()
        viewModel.updateSelectedTimeOfDelivery("")
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 10.dp, bottom = 20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                CustomTextInputField("Cuisines", viewModel.cuisines)
                CustomNumberInputField("Num of Dishes", viewModel.numOfDish)
                CustomNumberInputField("Max Budget", viewModel.maxBudget)
            }
            PrimaryButton(name = "Generate ideas", null) {
                viewModel.getRandomPlaylist()
                navController.navigate("Playlist Random")
            }
        }
    }
}
