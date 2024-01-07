package com.example.foodpanda_capstone

//import LocalDatabase
//import NetworkService
import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.foodpanda_capstone.view.ui.screen.DrawerItems
import com.example.foodpanda_capstone.view.ui.screen.HomeScreen
import androidx.navigation.navArgument
import com.example.foodpanda_capstone.model.LoginFormRepository
import com.example.foodpanda_capstone.model.NetworkServiceImpl
import com.example.foodpanda_capstone.model.PlaylistRepository
import com.example.foodpanda_capstone.view.ui.screen.EditPlaylistScreen
import com.example.foodpanda_capstone.view.ui.screen.HomeAppBar
import com.example.foodpanda_capstone.view.ui.screen.LoginScreen
import com.example.foodpanda_capstone.view.ui.screen.PlaylistConfirmScreen
import com.example.foodpanda_capstone.view.ui.screen.PlaylistFormScreen
import com.example.foodpanda_capstone.view.ui.screen.PlaylistListScreen
import com.example.foodpanda_capstone.view.ui.screen.PlaylistScreen
import com.example.foodpanda_capstone.view.ui.screen.foodItemConfirm
import com.example.foodpanda_capstone.view.ui.theme.BrandPrimary
import com.example.foodpanda_capstone.view.ui.theme.BrandSecondary
import com.example.foodpanda_capstone.view.ui.theme.FoodpandaCapstoneTheme
import com.example.foodpanda_capstone.view.ui.theme.NeutralBorder
import com.example.foodpanda_capstone.view.ui.theme.Typography
import kotlinx.coroutines.launch
import com.example.foodpanda_capstone.viewmodel.GeneralViewModelFactory
import com.example.foodpanda_capstone.viewmodel.LoginFormViewModel
import com.example.foodpanda_capstone.viewmodel.PlaylistViewModel
import kotlinx.coroutines.delay

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

    val networkService = NetworkServiceImpl()
    val userRepository = LoginFormRepository(networkService = networkService)
    val loginFormViewModel = LoginFormViewModel(userRepository)
    val loginViewModelFactory = GeneralViewModelFactory(
        viewModelClass = LoginFormViewModel::class.java,
        repository = userRepository,
        factory = ::LoginFormViewModel
    )

    val loginViewModel: LoginFormViewModel = viewModel(factory = loginViewModelFactory)

    val playlistViewModel: PlaylistViewModel = viewModel(factory = playlistViewModelFactory)

    var searchResult by remember { mutableStateOf("") }

    val scaffoldState = rememberTopAppBarState()
    val scope = rememberCoroutineScope()

    //sidenav bar list.
//              var selectedNavItem by remember { mutableStateOf(items[0]) }
    var isDrawerOpen by remember { mutableStateOf(false) }


    val drawerItem = listOf(
        DrawerItems(Icons.Default.Face, "Profile", 0, false),
        DrawerItems(Icons.Filled.Email, "Inbox", 32, true),
        DrawerItems(Icons.Filled.Favorite, "Favorite", 32, true),
        DrawerItems(Icons.Filled.Info, "Help Center", 0, false),
        DrawerItems(Icons.Filled.ThumbUp, "Invite Friends", 0, false)
    )

    val drawerItem2 = listOf(
//        DrawerItems(Icons.Default.Share, "Share", 0, false),
        DrawerItems(Icons.Filled.Star, "Rate", 0, false),
        DrawerItems(Icons.Filled.Settings, "Setting", 0, false),
        DrawerItems(Icons.Filled.MoreVert, "Terms & Conditions / Policy", 0, false),
        DrawerItems(Icons.Filled.ExitToApp, "Logout", 0, false),
        )

    val drawerItem3 = listOf(
        DrawerItems(Icons.Filled.Info, "Help Center", 0, false),
        DrawerItems(Icons.Filled.ThumbUp, "Invite Friends", 0, false)
    )

    val drawerItem4 = listOf(
        DrawerItems(Icons.Filled.Settings, "Setting", 0, false),
        DrawerItems(Icons.Filled.MoreVert, "Terms & Conditions / Policy", 0, false),
    )

    var selectedItem by remember {
        mutableStateOf(drawerItem[0])
    }

    val isLoggedIn by loginViewModel.loginState.collectAsState()
    Log.d("Navigation", "isLoggedIn: $isLoggedIn")

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    //Side Nav bar
    ModalNavigationDrawer(drawerContent = {
        ModalDrawerSheet {

            Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(BrandPrimary),
                    contentAlignment = Alignment.Center
                ) {

                    Column(
                        Modifier.wrapContentSize(),
                        verticalArrangement = Arrangement.SpaceAround,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.food_delivery),
                            contentDescription = "profile pic",
                            modifier = Modifier
                                .size(130.dp)
                                .clip(CircleShape)
                        )

                        if(isLoggedIn) {
                            Text(
                                text = "Mr User",
                                Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp),
                                fontSize = 22.sp,
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black)

                            )
                        } else {
                            ClickableText(
                                text = AnnotatedString("Login / Create Account"),
                                onClick = {
                                    // Navigate to the login screen when the link is clicked
                                    navController.navigate("Login Form")
                                    // Close the navigation drawer
                                    scope.launch {
                                        drawerState.close()
                                    }
                                },
                                modifier = Modifier.padding(8.dp),
                                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black)
                            )

                        }
                    }
                    Divider(
                        Modifier.align(Alignment.BottomCenter), thickness = 1.dp,
                        NeutralBorder
                    )


                }
                if(isLoggedIn) {
                    drawerItem.forEach {
                        NavigationDrawerItem(label = { Text(text = it.text) }, selected = it == selectedItem, onClick = {
                            selectedItem = it

                            scope.launch {
                                drawerState.close()
                            }

                        },
                            modifier = Modifier.padding(horizontal = 16.dp), icon = {
                                Icon(
                                    modifier = Modifier,
                                    imageVector = it.icon,
                                    contentDescription = it.text,
                                    tint = BrandPrimary
                                )
                            }, badge = {
                                if (it.hasBadge) {
                                    BadgedBox(badge = {
                                        Badge {
                                            Text(text = it.badgeCount.toString(), fontSize = 17.sp)
                                        }
                                    }) {

                                    }
                                }
                            }
                        )
                    }
//                Log.d("Drawer", "Building Logout item")
                    Divider(
                        thickness = 1.dp,
                        color = Color.DarkGray
                    )
                    drawerItem2.forEach {
                        NavigationDrawerItem(label = { Text(text = it.text) }, selected = it == selectedItem, onClick = {
                            selectedItem = it
//
                            scope.launch {
                                drawerState.close()
                                if (selectedItem.text == "Logout") {
                                    loginViewModel.logout()

//                                    Log.d("Navigation", "Current destination: ${navController.currentDestination?.route}")
                                    // Navigate to your logout destination

                                    // Navigate to the home screen and clear the back stack,
                                    // ensure that the home screen is a single top destination.
                                    navController.navigate("Home") {
                                        popUpTo(navController.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                    }

//                                    Log.d("Navigation", "Current destination after navigation: ${navController.currentDestination?.route}")

                                }

                            }

                        },
                            modifier = Modifier.padding(horizontal = 16.dp), icon = {
                                Icon(imageVector = it.icon, contentDescription = it.text, tint = BrandPrimary)
                            }
                        )

                    }
                } else {
                    drawerItem3.forEach {
                        NavigationDrawerItem(label = { Text(text = it.text) }, selected = it == selectedItem, onClick = {
                            selectedItem = it

                            scope.launch {
                                drawerState.close()
                            }

                        },
                            modifier = Modifier.padding(horizontal = 16.dp), icon = {
                                Icon(
                                    modifier = Modifier,
                                    imageVector = it.icon,
                                    contentDescription = it.text,
                                    tint = BrandPrimary
                                )
                            }, badge = {
                                if (it.hasBadge) {
                                    BadgedBox(badge = {
                                        Badge {
                                            Text(text = it.badgeCount.toString(), fontSize = 17.sp)
                                        }
                                    }) {

                                    }
                                }
                            }
                        )
                    }
//                Log.d("Drawer", "Building Logout item")
                    Divider(
                        thickness = 1.dp,
                        color = Color.DarkGray
                    )
                    drawerItem4.forEach {
                        NavigationDrawerItem(label = { Text(text = it.text) }, selected = it == selectedItem, onClick = {
                            selectedItem = it
//
                            scope.launch {
                                drawerState.close()
                            }

                        },
                            modifier = Modifier.padding(horizontal = 16.dp), icon = {
                                Icon(imageVector = it.icon, contentDescription = it.text, tint = BrandPrimary)
                            }
                        )
                    }
                }

            }
        }

    }, drawerState = drawerState) {

        Scaffold(
            topBar = {
                when (currentRoute) {
                    "Home" -> {
                        Surface(modifier = Modifier.drawBehind {
                            drawLine(
                                color = BrandSecondary,
                                start = Offset(0f, size.height),
                                end = Offset(size.width, size.height),
                                strokeWidth = 2f,
                            )
                        }) {
                            TopAppBar(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(min = 100.dp),
                                colors = TopAppBarDefaults.smallTopAppBarColors(
                                    containerColor = BrandPrimary,
                                    titleContentColor = Color.White,
                                ),
                                title = {
                                    Column(
                                        verticalArrangement = Arrangement.SpaceEvenly,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 16.dp, bottom = 8.dp, top = 16.dp)
                                    ) {
                                        Text(text = currentRoute, style = Typography.titleMedium)
                                        Text(
                                            text = "Address input here from database...",
                                            style = Typography.bodySmall
                                        )
                                        //Sample search bar to filter database
                                        SearchBar() { searchText -> searchResult = "Searching for: $searchText" }
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(text = searchResult, style = MaterialTheme.typography.bodyMedium)

                                },

                                navigationIcon = {
                                    IconButton(modifier = Modifier
                                        .padding(top = 14.dp)
                                        .size(40.dp)
                                        .background(BrandPrimary),
                                        onClick = {
//                                                        isDrawerOpen = true
                                            scope.launch {
                                                drawerState.open()
                                            }
                                        }) {


                                        Icon(Icons.Default.Menu, contentDescription = null, tint = Color.White)
                                    }
                                },
                            )
                        }
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(bottom = 25.dp, start = 15.dp, end = 15.dp)
            )
            {
                NavHost(
                    navController = navController,
                    startDestination = "Home", // TODO: Update to Home page when home page is ready
                    enterTransition = { scaleIntoContainer() },
                    exitTransition = { scaleOutOfContainer(targetScale = 0.9f) },
                    popEnterTransition = { scaleIntoContainer(initialScale = 1.1f) },
                    popExitTransition = { scaleOutOfContainer() }
                ) {
                    composable("Login Form") {
                        LoginScreen(loginFormViewModel = loginViewModel, navController = navController)
                    }
                    composable("Home") {
                        HomeScreen(navController)
                    }
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

                    composable("Home AppBar") { backStackEntry ->
                        HomeAppBar(navController)
                    }


                    composable("Playlist Confirm") { backStackEntry ->
                        PlaylistConfirmScreen(foodItemConfirm,navController)
                    }

//                    composable("Logout") {
//                        navController.navigate("Login Form") {
//                            popUpTo("Login Form") {inclusive = true}
//                        }
//                    }
                }

            }

        }
    }
}


//Sample search bar to filter database
@Composable
fun SearchBar(onSearch: (String) -> Unit) {
    var searchText by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
//            .padding(2.dp)
            .background(NeutralBorder)
    ) {
        BasicTextField(
            value = searchText,
            onValueChange = {
                searchText = it
                onSearch(it)
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = androidx.compose.ui.text.input.ImeAction.Search,
                keyboardType = KeyboardType.Text
            ),
            textStyle = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        )

        IconButton(
            onClick = { onSearch(searchText) },
            modifier = Modifier.background(MaterialTheme.colorScheme.primary)
        ) {
            Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = Color.White)
        }
    }
}
