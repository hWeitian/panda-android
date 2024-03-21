package com.example.foodpanda_capstone.view.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.foodpanda_capstone.R
import com.example.foodpanda_capstone.model.PlaylistOverview
import com.example.foodpanda_capstone.model.PlaylistRepository
import com.example.foodpanda_capstone.model.api.PlaylistApiClient
import com.example.foodpanda_capstone.model.api.PlaylistApiService
import com.example.foodpanda_capstone.view.ui.composable.LoadingScreen
import com.example.foodpanda_capstone.view.ui.composable.SectionTitleAndBtn
import com.example.foodpanda_capstone.view.ui.theme.Typography
import com.example.foodpanda_capstone.viewmodel.GeneralViewModelFactory
import com.example.foodpanda_capstone.viewmodel.PlaylistSectionViewModel

@Composable
fun PlaylistSectionScreen(navController: NavController, isPublic: Boolean, userId: String) {

    val apiService: PlaylistApiService = PlaylistApiClient.apiService
    val repository = PlaylistRepository(apiService)
    val viewModelFactory = GeneralViewModelFactory(
        viewModelClass = PlaylistSectionViewModel::class.java,
        repository = repository,
        factory = ::PlaylistSectionViewModel // This refers to the constructor of PlaylistSectionViewModel
    )
    val viewModel: PlaylistSectionViewModel = viewModel(factory = viewModelFactory)

    val categoryPlaylist by viewModel.categoryPlaylist.collectAsState()
    val cancelledPlaylist by viewModel.cancelledPlaylist.collectAsState()
    val otherPlaylist by viewModel.otherPlaylist.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(isPublic) {
        if (isPublic) {
            viewModel.getPublicPlaylist()
        } else {
            viewModel.getUserPlaylist(userId)
        }
    }

    Box(
        modifier = Modifier
            .padding(top = 5.dp)
            .fillMaxSize()
            .background(Color.White)
    ) {
        if (isLoading || categoryPlaylist == null) {
            LoadingScreen()
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(
                    top = dimensionResource(R.dimen.base_side_padding),
                    bottom = 40.dp
                ),
                horizontalArrangement = Arrangement.spacedBy(50.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                if (isPublic) {
                    items(categoryPlaylist.size) { index ->
                        PlaylistCard(categoryPlaylist[index]) { navController.navigate("Playlist/${categoryPlaylist[index].id}/${categoryPlaylist[index].name}") }
                    }
                } else {
                    item(
                        span = { GridItemSpan(2) }
                    ) {
                        Text(text = "Currently Subscribed", style = Typography.titleSmall)
                    }
                    items(otherPlaylist.size) { index ->
                        PlaylistCard(otherPlaylist[index]) { navController.navigate("Playlist/${otherPlaylist[index].id}/${otherPlaylist[index].name}") }
                    }
                    if (cancelledPlaylist.isNotEmpty()) {
                        item(
                            span = { GridItemSpan(2) }
                        ) {
                            Text(text = "Cancelled", style = Typography.titleSmall)
                        }
                        items(cancelledPlaylist.size) { index ->
                            PlaylistCard(cancelledPlaylist[index]) { navController.navigate("Playlist/${cancelledPlaylist[index].id}/${cancelledPlaylist[index].name}") }
                        }
                    }
                }
            }
        }
    }
}

