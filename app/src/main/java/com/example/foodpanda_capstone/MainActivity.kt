package com.example.foodpanda_capstone

//import LocalDatabase
//import NetworkService
import android.app.Activity
import android.content.Context
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedCard
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
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
import com.example.foodpanda_capstone.model.AuthRepository
import com.example.foodpanda_capstone.model.LoginFormRepository
import com.example.foodpanda_capstone.model.NetworkServiceImpl
import com.example.foodpanda_capstone.model.PlaylistRepository
import com.example.foodpanda_capstone.model.api.PlaylistApiClient
import com.example.foodpanda_capstone.model.api.PlaylistApiService
import com.example.foodpanda_capstone.view.ui.screen.SearchScreen
import com.example.foodpanda_capstone.view.ui.screen.EditPlaylistScreen
import com.example.foodpanda_capstone.view.ui.screen.HomeAppBar
import com.example.foodpanda_capstone.view.ui.screen.LoginScreen
import com.example.foodpanda_capstone.view.ui.screen.PlaylistConfirmScreen
import com.example.foodpanda_capstone.view.ui.screen.PlaylistFormScreen
import com.example.foodpanda_capstone.view.ui.screen.PlaylistListScreen
import com.example.foodpanda_capstone.view.ui.screen.PlaylistScreen
import com.example.foodpanda_capstone.view.ui.screen.foodItemConfirm
import com.example.foodpanda_capstone.view.ui.screen.PlaylistSectionScreen
import com.example.foodpanda_capstone.view.ui.screen.onBoardingScreen
import com.example.foodpanda_capstone.view.ui.screen.signUpForm
import com.example.foodpanda_capstone.view.ui.theme.BrandPrimary
import com.example.foodpanda_capstone.view.ui.theme.BrandSecondary
import com.example.foodpanda_capstone.view.ui.theme.FoodpandaCapstoneTheme
import com.example.foodpanda_capstone.view.ui.theme.NeutralBorder
import com.example.foodpanda_capstone.view.ui.theme.NeutralDivider
import com.example.foodpanda_capstone.view.ui.theme.Typography
import com.example.foodpanda_capstone.viewmodel.AuthViewModel
import kotlinx.coroutines.launch
import com.example.foodpanda_capstone.viewmodel.GeneralViewModelFactory
import com.example.foodpanda_capstone.viewmodel.LoginFormViewModel
import com.example.foodpanda_capstone.viewmodel.PlaylistViewModel
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(applicationContext)

        auth = FirebaseAuth.getInstance()

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


    val apiService: PlaylistApiService = PlaylistApiClient.apiService
    val playlistRepository = PlaylistRepository(apiService)

    val playlistViewModelFactory = GeneralViewModelFactory(
        viewModelClass = PlaylistViewModel::class.java,
        repository = playlistRepository,
        factory = ::PlaylistViewModel
    )

    val authRepository = AuthRepository(FirebaseAuth.getInstance())

    val authViewModelFactory = GeneralViewModelFactory(
        viewModelClass = AuthViewModel::class.java,
        repository = authRepository,
        factory = ::AuthViewModel
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

    val authViewModel: AuthViewModel = viewModel(factory = authViewModelFactory)

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

    val isLoggedIn by authViewModel.loginState.collectAsState()
    val isSignedUp by authViewModel.signupState.collectAsState()

    Log.d("Navigation", "isLoggedIn: $isLoggedIn")

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    //Side Nav bar
    ModalNavigationDrawer(drawerContent = {
        ModalDrawerSheet {

//            Column(Modifier.fillMaxSize().background(Color.White), verticalArrangement = Arrangement.spacedBy(12.dp)) {
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
                        val auth = FirebaseAuth.getInstance()
                        val currentUser = auth.currentUser

                        if (isLoggedIn || isSignedUp) {
                            val userName = currentUser?.displayName ?: "Hi user!"
                            Text(
                                text = "Hello, $userName",
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
                                    navController.navigate("onBoarding Screen")
                                    // Close the navigation drawer
                                    scope.launch {
                                        drawerState.close()
                                    }
                                },
                                modifier = Modifier.padding(8.dp),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                ),
                            )

                        }
                    }
                    Divider(
                        Modifier.align(Alignment.BottomCenter), thickness = 1.dp,
                        NeutralBorder
                    )


                }
                if (isLoggedIn || isSignedUp) {
                    drawerItem.forEach {
                        NavigationDrawerItem(label = { Text(text = it.text) },
                            selected = it == selectedItem,
                            onClick = {
                                selectedItem = it

                                scope.launch {
                                    drawerState.close()
                                }

                            },
                            modifier = Modifier.padding(horizontal = 16.dp),
                            icon = {
                                Icon(
                                    modifier = Modifier,
                                    imageVector = it.icon,
                                    contentDescription = it.text,
                                    tint = BrandPrimary
                                )
                            },
                            badge = {
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
                    var showDialog by remember { mutableStateOf(false) }

                    drawerItem2.forEach {
                        NavigationDrawerItem(label = { Text(text = it.text) },
                            selected = it == selectedItem,
                            onClick = {
                                selectedItem = it

                                scope.launch {
                                    drawerState.close()
                                    if (selectedItem.text == "Logout") {
                                        // Show Dialog when the user clicks on "Logout"
                                        showDialog =
                                            true // Assuming you have a boolean state variable to control the dialog visibility
                                    }
                                }
                            },
                            modifier = Modifier.padding(horizontal = 16.dp),
                            icon = {
                                Icon(imageVector = it.icon, contentDescription = it.text, tint = BrandPrimary)
                            }
                        )
                    }

// Outside of your composable function, preferably in your main activity or composable
                    if (showDialog) {
                        AlertDialog(modifier = Modifier
                            .padding(8.dp)
                            .clip(RoundedCornerShape(16.dp)),
                            onDismissRequest = {
                                // Handle dialog dismissal if needed
                                showDialog = false
                            }
                        ) {
                            Card(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .clip(RoundedCornerShape(16.dp)) // Adjust the corner radius as needed
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(16.dp))
                                ) {
                                    Text("Logout", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Text("Are you sure you want to logout?")
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.End
                                    ) {
                                        Button(
                                            onClick = {
                                                authViewModel.signOut()
                                                navController.navigate("Home") {
                                                    popUpTo(navController.graph.startDestinationId) {
                                                        saveState = true
                                                    }
                                                    launchSingleTop = true
                                                }
                                                showDialog = false
                                            }
                                        ) {
                                            Text("Logout")
                                        }
                                        Spacer(modifier = Modifier.width(16.dp))
                                        Button(
                                            onClick = {
                                                showDialog = false
                                            }
                                        ) {
                                            Text("Cancel")
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    drawerItem3.forEach {
                        NavigationDrawerItem(label = { Text(text = it.text) },
                            selected = it == selectedItem,
                            onClick = {
                                selectedItem = it

                                scope.launch {
                                    drawerState.close()
                                }

                            },
                            modifier = Modifier.padding(horizontal = 16.dp),
                            icon = {
                                Icon(
                                    modifier = Modifier,
                                    imageVector = it.icon,
                                    contentDescription = it.text,
                                    tint = BrandPrimary
                                )
                            },
                            badge = {
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
                        NavigationDrawerItem(label = { Text(text = it.text) },
                            selected = it == selectedItem,
                            onClick = {
                                selectedItem = it
//
                                scope.launch {
                                    drawerState.close()
                                }

                            },
                            modifier = Modifier.padding(horizontal = 16.dp),
                            icon = {
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
//                                        SearchBar() { searchText -> searchResult = "Searching for: $searchText" }
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
                    .padding(
                        bottom = 0.dp,
                        start = dimensionResource(R.dimen.base_side_padding),
                        end = dimensionResource(R.dimen.base_side_padding)
                    )
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
                        LoginScreen(viewModel = authViewModel, navController = navController)
                    }
                    composable("onBoarding Screen") {
                        onBoardingScreen(navController = navController)
                    }
                    composable("SignUp Form") {
                        signUpForm(authViewModel, navController = navController)
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
                    composable("EditPlaylist/{title}",
                        arguments = listOf(
                            navArgument("title") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        EditPlaylistScreen(navController, playlistViewModel)
                    }


                    composable("Home AppBar") { backStackEntry ->
                        HomeAppBar(navController)
                    }


                    composable("Playlist Confirm") { backStackEntry ->
                        PlaylistConfirmScreen(foodItemConfirm, navController)
                    }

                    composable("ViewCategoryPlaylist/{categoryName}/{title}",
                        arguments = listOf(
                            navArgument("title") { type = NavType.StringType },
                            navArgument("categoryName") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        val categoryName = backStackEntry.arguments?.getString("categoryName")
                        PlaylistSectionScreen(navController, categoryName)
                    }

                    composable("Search") { backStackEntry ->
                        SearchScreen(navController, playlistViewModel)
                    }

                }

            }

        }
    }
}
