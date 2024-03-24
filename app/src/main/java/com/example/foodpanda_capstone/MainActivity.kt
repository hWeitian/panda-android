package com.example.foodpanda_capstone

//import LocalDatabase
//import NetworkService
import PREF_KEY_CURRENT_ADDRESS
import PREF_KEY_CURRENT_CITY
import PREF_KEY_CURRENT_ZIPCODE
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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.*
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.foodpanda_capstone.model.AddressRepository
import com.example.foodpanda_capstone.model.AuthRepository
import com.example.foodpanda_capstone.model.LoginFormRepository
import com.example.foodpanda_capstone.model.NetworkServiceImpl
import com.example.foodpanda_capstone.model.PlaylistRepository
import com.example.foodpanda_capstone.model.api.PlaylistApiClient
import com.example.foodpanda_capstone.model.api.PlaylistApiService
import com.example.foodpanda_capstone.view.ui.screen.*
import com.example.foodpanda_capstone.view.ui.screen.PlaylistScreen
import com.example.foodpanda_capstone.view.ui.theme.BrandDark
import com.example.foodpanda_capstone.view.ui.theme.BrandHighlight
import com.example.foodpanda_capstone.view.ui.theme.BrandPrimary
import com.example.foodpanda_capstone.view.ui.theme.BrandSecondary
import com.example.foodpanda_capstone.view.ui.theme.FoodpandaCapstoneTheme
import com.example.foodpanda_capstone.view.ui.theme.NeutralBorder
import com.example.foodpanda_capstone.view.ui.theme.Typography
import com.example.foodpanda_capstone.viewmodel.AddressViewModel
import com.example.foodpanda_capstone.viewmodel.AuthViewModel
import kotlinx.coroutines.launch
import com.example.foodpanda_capstone.viewmodel.GeneralViewModelFactory
import com.example.foodpanda_capstone.viewmodel.GeneralViewModelFactoryDoubleParam
import com.example.foodpanda_capstone.viewmodel.LoginFormViewModel
import com.example.foodpanda_capstone.viewmodel.PlaylistViewModel
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import getStringSharedPreference

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    private val PREFS_ADDRESSDATA_KEY = "currentAddressPreferences"

    private val currentAddressDataPref = "saved_current_address_data"
    private val currentZipCodePref = "saved_current_zipcode"
    private val currentCityPref = "saved_current_city"

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
    val context = LocalContext.current

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
        factory = ::PlaylistViewModel,
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

    val addressRepository = AddressRepository()
    val addressViewModelFactory = GeneralViewModelFactoryDoubleParam(
        viewModelClass = AddressViewModel::class.java,
        repository = addressRepository,
        factory = ::AddressViewModel,
        context = context,
    )

    val loginViewModel: LoginFormViewModel = viewModel(factory = loginViewModelFactory)

    val playlistViewModel: PlaylistViewModel = viewModel(factory = playlistViewModelFactory)

    val authViewModel: AuthViewModel = viewModel(factory = authViewModelFactory)

    val addressFormViewModel: AddressViewModel = viewModel(factory = addressViewModelFactory)

    var searchResult by remember { mutableStateOf("") }


    val scaffoldState = rememberTopAppBarState()
    val scope = rememberCoroutineScope()

    var isDrawerOpen by remember { mutableStateOf(false) }


    val drawerItem = listOf(
        DrawerItems(Icons.Default.Face, "Profile", 0, false),
        DrawerItems(Icons.Filled.Favorite, "Favorite", 32, true),
        DrawerItems(Icons.Filled.ThumbUp, "Invite Friends", 0, false)
    )

    val drawerItem2 = listOf(
        DrawerItems(Icons.Filled.Star, "Rate", 0, false),
        DrawerItems(Icons.Filled.Settings, "Setting", 0, false),
        DrawerItems(Icons.Filled.ExitToApp, "Logout", 0, false),
    )

    val drawerItem3 = listOf(
        DrawerItems(Icons.Filled.ThumbUp, "Invite Friends", 0, false)
    )

    val drawerItem4 = listOf(
        DrawerItems(Icons.Filled.Settings, "Setting", 0, false),
        DrawerItems(Icons.Filled.MoreVert, "Terms & Conditions / Policy", 0, false),
    )

    var selectedItem by remember {
        mutableStateOf(drawerItem[0])
    }

//    val isLoggedIn by authViewModel.loginState.collectAsState()
//    val isSignedUp by authViewModel.signupState.collectAsState()

    // For testing purpose
    val isLoggedIn = true
    val isSignedUp = true

    Log.d("Navigation", "isLoggedIn: $isLoggedIn")
    Log.d("Navigation", "isSignedUp: $isSignedUp")

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val sheetState = rememberModalBottomSheetState()


    var isAddressFormVisible by remember { mutableStateOf(false) }
    // Function to toggle the visibility of the AddressFormScreen
    val toggleAddressFormVisibility = { isAddressFormVisible = !isAddressFormVisible }


    val savedAddress = context.getStringSharedPreference(PREF_KEY_CURRENT_ADDRESS)
    val savedZipCode = context.getStringSharedPreference(PREF_KEY_CURRENT_ZIPCODE)
    val savedCity = context.getStringSharedPreference(PREF_KEY_CURRENT_CITY)


    var selectedAddress = if (savedAddress != null && savedZipCode != null && savedCity != null) {
        "$savedAddress $savedZipCode $savedCity"
    } else {
        "Type your address here"
    }

    // Define the setAddressOnUI function
    val setAddressOnAppbar: (String, String, String) -> Unit = { address, city, zipCode ->
        // Here you can use address, city, and zipCode as needed
        // For example, you could concatenate them into one string:
        selectedAddress = "$address, $city, $zipCode"
    }

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
                                    navController.navigate("Welcome")
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
                                        verticalArrangement = Arrangement.spacedBy(8.dp),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 16.dp, bottom = 8.dp, top = 16.dp)
                                            .clickable {
                                                toggleAddressFormVisibility()
                                                isAddressFormVisible = true
//                                                navController.navigate("Address Form Screen")

                                            }
                                    ) {
                                        Text(text = currentRoute, style = Typography.titleMedium)
                                        selectedAddress?.let {
                                            Text(
                                                text = it,
                                                style = Typography.bodyLarge
                                            )
                                        }

                                        if (isAddressFormVisible) {
                                            AddressFormScreen(
                                                addressViewModel = addressFormViewModel,
                                                isVisible = isAddressFormVisible,
                                                showBottomSheet = isAddressFormVisible,
                                                toggleBottomSheet = { toggleAddressFormVisibility() },
                                                setAddressOnAppbar = setAddressOnAppbar,
                                                onAddressSelected = selectedAddress
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                },
                                navigationIcon = {
                                    IconButton(modifier = Modifier
                                        .padding(top = 24.dp)
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
                                    IconButton(onClick = {
                                        if (currentRoute == "Playlists") navController.navigate("Home") else navController.popBackStack()
                                    }) {
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
                    modifier = Modifier.fillMaxHeight(),
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
                    composable("Welcome") {
                        onBoardingScreen(navController = navController)
                    }
                    composable("SignUp Form") {
                        signUpForm(authViewModel, navController = navController)
                    }
                    composable("Home") {
                        HomeScreen(navController)
                    }
                    composable(
                        "Playlists",
                    ) {
                        PlaylistListScreen(
                            navController = navController,
                            isUserLoggedIn = isLoggedIn || isSignedUp,
                            userId = "1",
                            showSnackbar = playlistViewModel.shouldShowSnackbar,
                            snackbarMessage = playlistViewModel.snackbarMessage,
                            resetSnackbar = playlistViewModel::resetSnackbarState
                        )
                    }
                    composable(
                        "Build your mix",
                    ) {
                        PlaylistFormScreen(navController, playlistViewModel)
                    }
                    composable(
                        "Playlist/{playlistId}/{title}",
                        arguments = listOf(
                            navArgument("playlistId") { type = NavType.IntType },
                            navArgument("title") { type = NavType.StringType }
                        ),
                    ) { backStackEntry ->
                        val playlistId = backStackEntry.arguments?.getInt("playlistId")
                        PlaylistScreen(
                            navController = navController,
                            id = playlistId,
                            viewModel = playlistViewModel,
                            isUserLoggedIn = isLoggedIn || isSignedUp,
                        )
                    }
                    composable("EditPlaylist/{title}",
                        arguments = listOf(
                            navArgument("title") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        EditPlaylistScreen(
                            navController = navController,
                            viewModel = playlistViewModel,
                        )
                    }
                    composable("Home AppBar") { backStackEntry ->
                        HomeAppBar(navController)
                    }
                    composable("Confirmation") { backStackEntry ->
                        PlaylistConfirmScreen(playlistViewModel, navController)
                    }
                    composable("ViewCategoryPlaylist/{title}/{isPublic}/{userId}",
                        arguments = listOf(
                            navArgument("title") { type = NavType.StringType },
                            navArgument("isPublic") { type = NavType.BoolType },
                            navArgument("userId") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        val userId = backStackEntry.arguments?.getString("userId")
                        val isPublic = backStackEntry.arguments?.getBoolean("isPublic", false)
                        if (isPublic != null && userId != null) {
                            PlaylistSectionScreen(navController, isPublic, userId)
                        }
                    }
                    composable("Search") { backStackEntry ->
                        SearchScreen(navController, playlistViewModel)
                    }
                    composable("Playlist Random") { backStackEntry ->
                        PlaylistScreen(
                            navController = navController,
                            id = null,
                            viewModel = playlistViewModel,
                            isUserLoggedIn = isLoggedIn || isSignedUp,
                        )
                    }
                }
            }
        }
    }
}
