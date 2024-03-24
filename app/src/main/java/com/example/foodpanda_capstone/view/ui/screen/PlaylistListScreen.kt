package com.example.foodpanda_capstone.view.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.foodpanda_capstone.model.PlaylistOverview
import com.example.foodpanda_capstone.model.PlaylistRepository
import com.example.foodpanda_capstone.model.api.PlaylistApiClient
import com.example.foodpanda_capstone.model.api.PlaylistApiService
import com.example.foodpanda_capstone.utils.grayScale
import com.example.foodpanda_capstone.utils.truncateString
import com.example.foodpanda_capstone.view.ui.composable.CustomTextBtn
import com.example.foodpanda_capstone.view.ui.composable.ImageHolder
import com.example.foodpanda_capstone.view.ui.composable.LoadingScreen
import com.example.foodpanda_capstone.view.ui.composable.PrimaryButton
import com.example.foodpanda_capstone.view.ui.composable.ScreenBottomSpacer
import com.example.foodpanda_capstone.view.ui.composable.SectionTitleAndBtn
import com.example.foodpanda_capstone.view.ui.theme.BrandDark
import com.example.foodpanda_capstone.view.ui.theme.BrandSecondary
import com.example.foodpanda_capstone.view.ui.theme.Typography
import com.example.foodpanda_capstone.viewmodel.AllPlaylistViewModel
import com.example.foodpanda_capstone.viewmodel.GeneralViewModelFactory
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Composable
fun PlaylistListScreen(
    navController: NavController,
    isUserLoggedIn: Boolean,
    userId: String = "1",
    showSnackbar: StateFlow<Boolean>,
    snackbarMessage: String,
    resetSnackbar: () -> Unit
) {

    val apiService: PlaylistApiService = PlaylistApiClient.apiService
    val repository = PlaylistRepository(apiService)
    val viewModelFactory = GeneralViewModelFactory(
        viewModelClass = AllPlaylistViewModel::class.java,
        repository = repository,
        factory = ::AllPlaylistViewModel,
    )
    val viewModel: AllPlaylistViewModel = viewModel(factory = viewModelFactory)

    val publicPlaylists by viewModel.publicPlaylists.collectAsState()
    val userPlaylists by viewModel.userPlaylists.collectAsState()
    val cancelledPlaylist by viewModel.cancelledPlaylist.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val shouldShowSnackbar by showSnackbar.collectAsState()

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(userId) {
        viewModel.getAllPlaylist(userId)
    }

    LaunchedEffect(shouldShowSnackbar) {
        if (shouldShowSnackbar) {
            scope.launch {
                snackbarHostState.showSnackbar(snackbarMessage)
            }
            resetSnackbar()
        }
    }

    if (isLoading) {
        LoadingScreen()
    } else {
        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState) {
                    Snackbar(
                        snackbarData = it,
                        containerColor = BrandDark,
                        contentColor = Color.White
                    )
                }
            }
        ) { contentPadding ->
            Column(
                modifier = Modifier
                    .padding(contentPadding)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {

                /*
            Start to disable user Login
            */

//                Column {
//                    PlaylistSection(
//                        dataList = userPlaylists,
//                        title = "Your Subscriptions",
//                        navController = navController,
//                        isPublic = false,
//                        userId = userId
//                    )
//                    PlaylistSection(
//                        dataList = publicPlaylists,
//                        title = "Discover More",
//                        navController = navController,
//                        isPublic = true,
//                        userId = userId
//                    )
//                }
//                PlaylistListScreenButtons(
//                    descriptionText = "Want a tailored experience?",
//                    buttonText = "Build your mix!",
//                    navigateDestination = "Build your mix",
//                    navController,
//                    Modifier.weight(1f)
//                )

                /*
            Start to enable user Login
            */

                if (!isUserLoggedIn) {
                    PlaylistListScreenButtons(
                        descriptionText = "Log in / sign up to view your subscriptions",
                        buttonText = "Log in / Sign up",
                        navigateDestination = "Welcome",
                        navController = navController,
                        modifier = Modifier.weight(1f)
                    )
                    Column {
                        PlaylistSection(
                            dataList = publicPlaylists,
                            title = "Discover More",
                            navController = navController,
                            isPublic = true,
                            userId = userId
                        )
                    }
                } else if (userPlaylists.isNullOrEmpty() && cancelledPlaylist.isNullOrEmpty()) {
                    PlaylistListScreenButtons(
                        descriptionText = "You haven't subscribed to any playlists.",
                        buttonText = "Build your mix!",
                        navigateDestination = "Build your mix",
                        navController = navController,
                        modifier = Modifier.weight(1f)
                    )
                    Column {
                        PlaylistSection(
                            dataList = publicPlaylists,
                            title = "Discover More",
                            navController = navController,
                            isPublic = true,
                            userId = userId
                        )
                    }
                } else if (userPlaylists.isNullOrEmpty() && cancelledPlaylist.isNotEmpty()) {
                    PlaylistListScreenButtons(
                        descriptionText = "You haven't subscribed to any playlists.",
                        buttonText = "Build your mix!",
                        navigateDestination = "Build your mix",
                        secondaryButtonText = "Click here to view your cancelled playlist",
                        secondaryDestination = "ViewCategoryPlaylist/Your Subscription/${false}/$userId",
                        navController = navController,
                        modifier = Modifier.weight(1f)
                    )
                    Column {
                        PlaylistSection(
                            dataList = publicPlaylists,
                            title = "Discover More",
                            navController = navController,
                            isPublic = true,
                            userId = userId
                        )
                    }
                } else {
                    Column {
                        PlaylistSection(
                            dataList = userPlaylists,
                            title = "Your Subscription",
                            navController = navController,
                            isPublic = false,
                            userId = userId
                        )
                        PlaylistSection(
                            dataList = publicPlaylists,
                            title = "Discover More",
                            navController = navController,
                            isPublic = true,
                            userId = userId
                        )
                    }
                    PlaylistListScreenButtons(
                        descriptionText = "Want a tailored experience?",
                        buttonText = "Build your mix!",
                        navigateDestination = "Build your mix",
                        navController = navController,
                        modifier = Modifier.weight(1f)
                    )
                }

                ScreenBottomSpacer()
            }
        }
    }
}

@Composable
fun PlaylistListScreenButtons(
    descriptionText: String,
    buttonText: String,
    navigateDestination: String,
    navController: NavController,
    secondaryButtonText: String? = null,
    secondaryDestination: String? = null,
    modifier: Modifier
) {
    Column(
        modifier
            .fillMaxWidth()
            .padding(top = 25.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = descriptionText, style = Typography.titleSmall)
            Spacer(modifier = Modifier.size(15.dp))
            if (secondaryButtonText != null && secondaryDestination != null) {
                CustomTextBtn(secondaryButtonText, null, null) { navController.navigate(secondaryDestination) }
                Spacer(modifier = Modifier.size(15.dp))
            }
            PrimaryButton(name = buttonText, null) {
                navController.navigate(navigateDestination)
            }
        }

    }
}

@Composable
fun PlaylistSection(
    dataList: List<PlaylistOverview>,
    title: String,
    navController: NavController,
    isPublic: Boolean,
    userId: String
) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .padding(vertical = 10.dp)
    ) {

        SectionTitleAndBtn(
            title = title, btnTitle = "See all", icon = null,
            modifier = Modifier
        ) {
            navController.navigate("ViewCategoryPlaylist/$title/$isPublic/$userId")
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
                    if (index == 0 && dataList.size >= 3) {
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
fun PlaylistCard(playlist: PlaylistOverview, cardClicked: () -> Unit) {

    val modifier = if (playlist.status == "Cancelled") Modifier
        .width(150.dp)
        .grayScale()
        .clickable { cardClicked() }
    else Modifier
        .width(150.dp)
        .clickable { cardClicked() }

    Column(
        modifier = modifier
    ) {
        playlist.imageUrl?.let { ImageHolder(it, null, "Playlist Image") }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = playlist.name.truncateString(),
                style = Typography.headlineLarge,
                modifier = Modifier.weight(0.8f)
            )
            Text(
                text = "S$ ${"%.2f".format(playlist.cost)}",
                style = Typography.headlineLarge,
                fontWeight = FontWeight.Normal
            )
        }
        if (playlist.isPublic == false && playlist.status != "Cancelled") {
            Text(text = "Deliver every ${playlist.deliveryDay}", style = Typography.bodyMedium, color = BrandSecondary)
        }
    }
}
