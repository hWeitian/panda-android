package com.example.foodpanda_capstone.view.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.RamenDining
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.foodpanda_capstone.R
import com.example.foodpanda_capstone.view.ui.composable.CustomNumberInputField
import com.example.foodpanda_capstone.view.ui.composable.CustomTextInputField
import com.example.foodpanda_capstone.view.ui.composable.PrimaryButton
import com.example.foodpanda_capstone.view.ui.theme.BrandPrimary
import com.example.foodpanda_capstone.view.ui.theme.Typography
import com.example.foodpanda_capstone.viewmodel.PlaylistViewModel

@Composable
fun PlaylistFormScreen(navController: NavController, viewModel: PlaylistViewModel) {

    val cuisines by viewModel.cuisines.observeAsState("")
    val numOfDish by viewModel.numOfDish.observeAsState("")
    val maxBudget by viewModel.maxBudget.observeAsState("")
    val isError by viewModel.isError.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.updateSelectedTimeOfDelivery("")
        if (!isError) {
            viewModel.clearFormText()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 25.dp, bottom = 20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
            ) {
                Text(text = "Build your", style = Typography.titleLarge)
                Text(text = "random food playlist", style = Typography.titleLarge, color = BrandPrimary)
                Spacer(modifier = Modifier.size(25.dp))
                OrderedListItem(
                    num = "1 - ",
                    title = "Keyword",
                    description = "Tell us your craving (e.g., spicy, italian, burgers, all)"
                )
                Spacer(modifier = Modifier.size(10.dp))
                OrderedListItem(
                    num = "2 - ",
                    title = "Number of dishes",
                    description = "Pick your portions, how many dishes are you up for?"
                )
                Spacer(modifier = Modifier.size(10.dp))
                OrderedListItem(
                    num = "3 - ",
                    title = "Budget",
                    description = "Let us know your spending limit"
                )
                Spacer(modifier = Modifier.size(10.dp))
                OrderedListItem(
                    num = "4 - ",
                    title = "Generate",
                    description = "Click generate to build your random food playlist"
                )
                Spacer(modifier = Modifier.size(5.dp))
                Column {
                    CustomTextInputField(
                        label = "Keyword",
                        inputValue = cuisines,
                        updateInput = viewModel::updateCuisines,
                        icon = Icons.Default.RamenDining
                    )
                    CustomNumberInputField(
                        label = "Number of dishes",
                        inputValue = numOfDish.toString(),
                        updateInput = viewModel::updateNumOfDish,
                        icon = ImageVector.vectorResource(R.drawable.dish)
                    )
                    CustomNumberInputField(
                        label = "Budget",
                        inputValue = maxBudget.toString(),
                        updateInput = viewModel::updateMaxBudget,
                        icon = Icons.Default.AttachMoney
                    )
                }
                Spacer(modifier = Modifier.size(50.dp))
                PrimaryButton(
                    name = "Generate",
                    width = null,
                    isEnabled = !(cuisines.isBlank() || numOfDish.isBlank() || maxBudget.isBlank())
                ) {
                    viewModel.getRandomPlaylist()
                    navController.navigate("Playlist Random")
                }
            }
        }
    }
}


@Composable
fun OrderedListItem(num: String, title: String?, description: String?) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row {
            Text(text = "Step $num", style = Typography.bodyLarge, fontWeight = FontWeight.Bold)
            if (title != null) {
                Text(text = title, style = Typography.bodyLarge, fontWeight = FontWeight.Bold)
            }
        }
    }
    if (description != null) {
        Text(text = description, style = Typography.bodyLarge)
    }
}
