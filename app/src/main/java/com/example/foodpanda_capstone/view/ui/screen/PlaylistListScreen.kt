package com.example.foodpanda_capstone.view.ui.screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.foodpanda_capstone.model.Playlist
import com.example.foodpanda_capstone.model.PlaylistCategory
import com.example.foodpanda_capstone.model.PlaylistOverview
import com.example.foodpanda_capstone.model.PlaylistRepository
import com.example.foodpanda_capstone.model.api.PlaylistApiClient
import com.example.foodpanda_capstone.model.api.PlaylistApiService
import com.example.foodpanda_capstone.view.ui.composable.ImageHolder
import com.example.foodpanda_capstone.view.ui.composable.LoadingScreen
import com.example.foodpanda_capstone.view.ui.composable.PrimaryButton
import com.example.foodpanda_capstone.view.ui.composable.ScreenBottomSpacer
import com.example.foodpanda_capstone.view.ui.composable.SectionTitleAndBtn
import com.example.foodpanda_capstone.view.ui.theme.BrandSecondary
import com.example.foodpanda_capstone.view.ui.theme.Typography
import com.example.foodpanda_capstone.viewmodel.AllPlaylistViewModel
import com.example.foodpanda_capstone.viewmodel.GeneralViewModelFactory

@Composable
fun PlaylistListScreen(navController: NavController) {

    val apiService: PlaylistApiService = PlaylistApiClient.apiService
    val repository = PlaylistRepository(apiService)
    val viewModelFactory = GeneralViewModelFactory(
        viewModelClass = AllPlaylistViewModel::class.java,
        repository = repository,
        factory = ::AllPlaylistViewModel // This refers to the constructor of AllPlaylistViewModel
    )
    val viewModel: AllPlaylistViewModel = viewModel(factory = viewModelFactory)

    val publicPlaylists by viewModel.publicPlaylists.collectAsState()
    val userPlaylists by viewModel.userPlaylists.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    if(isLoading) {
        LoadingScreen()
    } else {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Column {
                PlaylistSection(userPlaylists, "Your Subscription", navController)
                PlaylistSection(publicPlaylists, "Discover More", navController)
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(top = 25.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Column (
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Want a tailored experience?", style = Typography.titleMedium)
                    Spacer(modifier = Modifier.size(10.dp))
                    PrimaryButton(name = "Build your mix!", null) {
                        navController.navigate("Playlist Form")
                    }
                }

            }
            ScreenBottomSpacer()
        }
    }

}

@Composable
fun PlaylistListScreenButtons(navController: NavController) {
    Column(
        modifier = Modifier
//            .weight(1f)
            .fillMaxWidth()
            .padding(top = 25.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Column (
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Want a tailored experience?", style = Typography.titleMedium)
            Spacer(modifier = Modifier.size(10.dp))
            PrimaryButton(name = "Build your mix!", null) {
                navController.navigate("Playlist Form")
            }
        }

    }
}

@Composable
fun PlaylistSection(dataList: List<PlaylistOverview>, title: String, navController: NavController) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .padding(vertical = 10.dp)
    ) {

        SectionTitleAndBtn(title = title, btnTitle = "See all", icon = null) {
            navController.navigate("ViewCategoryPlaylist/$title/$title")
        }

        Box(modifier = Modifier.layout() { measurable, constraints ->
            // calculate end padding
            val placeable = measurable.measure(
                constraints.copy(
                    maxWidth = constraints.maxWidth + 20.dp.roundToPx(),
                )
            )
            layout(placeable.width, placeable.height) {
                placeable.place(0.dp.roundToPx(), 0)
            }
        }) {
            LazyRow(
                modifier = Modifier
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                items(dataList.size) { index ->
                    if (index == 0) {
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                    PlaylistCard(dataList[index]) { navController.navigate("Playlist/${dataList[index].id}/${dataList[index].name}") }
                    if (index == dataList.size - 1) {
                        Spacer(modifier = Modifier.width(15.dp))
                    }
                }
            }
        }


    }
}


@Composable
fun PlaylistCard(playlist: Playlist, cardClicked: () -> Unit) {
    Column(
        modifier = Modifier
            .width(150.dp)
            .clickable { cardClicked() }
    ) {

        playlist.imageUrl?.let { ImageHolder(it, 140, "Playlist Image") }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = playlist.name, style = Typography.headlineLarge, modifier = Modifier.width(85.dp))
            Text(
                text = "S$ ${"%.2f".format(playlist.cost)}",
                style = Typography.headlineLarge,
                fontWeight = FontWeight.Normal
            )
        }
        Text(text = "Deliver every ${playlist.deliveryDay}", style = Typography.bodyMedium, color = BrandSecondary)
    }
}
