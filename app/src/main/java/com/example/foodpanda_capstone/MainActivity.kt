package com.example.foodpanda_capstone

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.get
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.example.foodpanda_capstone.model.PlaylistRepository
import com.example.foodpanda_capstone.view.ui.screen.EditPlaylistScreen
import com.example.foodpanda_capstone.view.ui.screen.PlaylistFormScreen
import com.example.foodpanda_capstone.view.ui.screen.PlaylistListScreen
import com.example.foodpanda_capstone.view.ui.screen.PlaylistScreen
import com.example.foodpanda_capstone.view.ui.theme.BrandSecondary
import com.example.foodpanda_capstone.view.ui.theme.FoodpandaCapstoneTheme
import com.example.foodpanda_capstone.view.ui.theme.Typography
import com.example.foodpanda_capstone.viewmodel.AllPlaylistViewModel
import com.example.foodpanda_capstone.viewmodel.GeneralViewModelFactory
import com.example.foodpanda_capstone.viewmodel.PlaylistViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodpandaCapstoneTheme {
                Navigation()

            }
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation() {
    fun scaleIntoContainer(
        initialScale: Float = 0.9f
    ): EnterTransition {
        return scaleIn(
            animationSpec = tween(220, delayMillis = 90),
            initialScale = initialScale
        ) + fadeIn(animationSpec = tween(220, delayMillis = 90))
    }

    fun scaleOutOfContainer(
        targetScale: Float = 1.1f
    ): ExitTransition {
        return scaleOut(
            animationSpec = tween(
                durationMillis = 220,
                delayMillis = 90
            ), targetScale = targetScale
        ) + fadeOut(tween(delayMillis = 90))
    }

    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route ?: ""
    val routeOfPreviousEntry = navController.previousBackStackEntry?.destination?.route
    val currentArguments = navController.currentBackStackEntryAsState().value?.arguments
    val pageTitle = currentArguments?.getString("title")

    // Get the current activity and window insets
    val activity = LocalView.current.context as Activity
    val windowInsets = WindowCompat.getInsetsController(activity.window, activity.window.decorView)

    // Set the status bar background colour depending on the current route
    val statusBarBackgroundArgb = when (currentRoute) {
        "home" -> Color(R.color.brand_primary).toArgb()
        else -> Color.White.toArgb()
    }

    LaunchedEffect(statusBarBackgroundArgb) {
        // Change status bar background colour
        activity.window.statusBarColor = statusBarBackgroundArgb
        // If status bar is white, change icons and text to black
        windowInsets.isAppearanceLightStatusBars = statusBarBackgroundArgb == Color.White.toArgb()
    }

    val playlistRepository = PlaylistRepository()
    val playlistViewModelFactory = GeneralViewModelFactory(
        viewModelClass = PlaylistViewModel::class.java,
        repository = playlistRepository,
        factory = ::PlaylistViewModel
    )
    val playlistViewModel: PlaylistViewModel = viewModel(factory = playlistViewModelFactory)

    Scaffold(
        containerColor = Color.White,
        topBar = {
            when (currentRoute) {
                "home" -> {
                    // TODO: Put Home page TopAppBar here
                }

                else -> {
                    Surface(modifier = Modifier.drawBehind {
                        drawLine(
                            color = BrandSecondary,
                            start = Offset(0f, size.height),
                            end = Offset(size.width, size.height),
                            strokeWidth = 2f,
                        )
                    }) {
                        TopAppBar(
                            colors = TopAppBarDefaults.smallTopAppBarColors(
                                containerColor = Color.White,
                                titleContentColor = Color.Black,
                            ),
                            title = {
                                Text(text = pageTitle ?: currentRoute, style = Typography.titleMedium)
                            },
                            navigationIcon = {
                                IconButton(onClick = { navController.popBackStack() }) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_arrow_tail_back),
                                        contentDescription = "Back Button",
                                        modifier = Modifier.size(25.dp)
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        when (currentRoute) {
            "home" -> {
                // TODO: Put Home page Composable here
            }

            else -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(bottom = 25.dp, start = 15.dp, end = 15.dp)
                )
                {
                    NavHost(
                        navController = navController,
                        startDestination = "Playlist List", // TODO: Update to Home page when home page is ready
                        enterTransition = { scaleIntoContainer() },
                        exitTransition = { scaleOutOfContainer(targetScale = 0.9f) },
                        popEnterTransition = { scaleIntoContainer(initialScale = 1.1f) },
                        popExitTransition = { scaleOutOfContainer() }
                    ) {
                        composable(
                            "Playlist List",
                        ) {
                            PlaylistListScreen(navController)
                        }
                        composable(
                            "Playlist Form",
                        ) {
                            PlaylistFormScreen(navController)
                        }
                        composable(
                            "Playlist/{playlistId}/{title}",
                            arguments = listOf(
                                navArgument("playlistId") { type = NavType.IntType },
                                navArgument("title") { type = NavType.StringType }
                            ),
                        ) { backStackEntry ->
                            val playlistId = backStackEntry.arguments?.getInt("playlistId")
                            PlaylistScreen(navController, playlistId, playlistViewModel)
                        }
                        composable("EditPlaylist") { backStackEntry ->
                            EditPlaylistScreen(navController, playlistViewModel)
                        }
                    }
                }
            }
        }
    }
}
