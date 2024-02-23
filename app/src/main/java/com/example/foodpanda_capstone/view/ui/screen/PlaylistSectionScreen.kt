package com.example.foodpanda_capstone.view.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.foodpanda_capstone.R
import com.example.foodpanda_capstone.model.PlaylistRepository
import com.example.foodpanda_capstone.model.api.PlaylistApiClient
import com.example.foodpanda_capstone.model.api.PlaylistApiService
import com.example.foodpanda_capstone.view.ui.composable.LoadingScreen
import com.example.foodpanda_capstone.view.ui.composable.PrimaryButton
import com.example.foodpanda_capstone.view.ui.composable.ScreenBottomSpacer
import com.example.foodpanda_capstone.view.ui.theme.Typography
import com.example.foodpanda_capstone.viewmodel.GeneralViewModelFactory
import com.example.foodpanda_capstone.viewmodel.PlaylistSectionViewModel

@Composable
fun PlaylistSectionScreen(navController: NavController, categoryName: String?) {

    val apiService: PlaylistApiService = PlaylistApiClient.apiService
    val repository = PlaylistRepository(apiService)
    val viewModelFactory = GeneralViewModelFactory(
        viewModelClass = PlaylistSectionViewModel::class.java,
        repository = repository,
        factory = ::PlaylistSectionViewModel // This refers to the constructor of PlaylistSectionViewModel
    )
    val viewModel: PlaylistSectionViewModel = viewModel(factory = viewModelFactory)

    val categoryPlaylist by viewModel.categoryPlaylist.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(categoryName) {
        if (categoryName != null) {
            viewModel.getCategoryPlaylist(categoryName)
        }
    }

    Box(
        modifier = Modifier
            .padding(top = 5.dp)
            .fillMaxSize()
            .background(Color.White)
    ) {
        if (!isLoading && categoryPlaylist != null) {
            val dataList = categoryPlaylist
            Column(modifier = Modifier.fillMaxSize()) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(
                        start = dimensionResource(R.dimen.base_side_padding),
                        end = dimensionResource(R.dimen.base_side_padding),
                        top = dimensionResource(R.dimen.base_side_padding),
                        bottom = 50.dp
                    ),
                    horizontalArrangement = Arrangement.spacedBy(50.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    items(dataList.size) { index ->
                        PlaylistCard(dataList[index]) { navController.navigate("Playlist/${dataList[index].id}/${dataList[index].name}") }
                    }
                }
            }
        } else {
            LoadingScreen()
        }
    }


}
