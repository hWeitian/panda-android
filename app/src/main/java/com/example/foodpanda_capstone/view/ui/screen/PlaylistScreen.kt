package com.example.foodpanda_capstone.view.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.foodpanda_capstone.model.Playlist
import com.example.foodpanda_capstone.model.PlaylistRepository
import com.example.foodpanda_capstone.viewmodel.GeneralViewModelFactory
import com.example.foodpanda_capstone.viewmodel.PlaylistViewModel
import kotlinx.coroutines.runBlocking

@Composable
fun PlaylistScreen(navController: NavController, id: Int?) {

    val repository = PlaylistRepository()
    val viewModelFactory = GeneralViewModelFactory(
        viewModelClass = PlaylistViewModel::class.java,
        repository = repository,
        factory = ::PlaylistViewModel
    )
    val viewModel: PlaylistViewModel = viewModel(factory = viewModelFactory)

    val currentPlaylist by viewModel.currentPlaylist.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(id) {
        if (id != null) {
            viewModel.getOnePlaylist(id)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        if(!isLoading){
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())

            ) {
                Text(text = id.toString())
                currentPlaylist?.let {
                    Text(text = it.name)
                    Text(text = it.deliverDay)
                }

            }
        } else {
            Text(text = "Loading...")
        }

    }

}
