package com.example.foodpanda_capstone

import android.app.Activity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.EnterTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.SemanticsProperties.ImeAction
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.foodpanda_capstone.view.ui.screen.DrawerBody
import com.example.foodpanda_capstone.view.ui.screen.DrawerHeader
import com.example.foodpanda_capstone.view.ui.screen.DrawerItems
import com.example.foodpanda_capstone.view.ui.screen.HomeAppBar
import com.example.foodpanda_capstone.view.ui.screen.HomeScreen
import com.example.foodpanda_capstone.view.ui.screen.MenuItems
import com.example.foodpanda_capstone.view.ui.screen.PlaylistFormScreen
import com.example.foodpanda_capstone.view.ui.screen.PlaylistScreen
import com.example.foodpanda_capstone.view.ui.theme.BrandPrimary
import com.example.foodpanda_capstone.view.ui.theme.BrandSecondary
import com.example.foodpanda_capstone.view.ui.theme.FoodpandaCapstoneTheme
import com.example.foodpanda_capstone.view.ui.theme.NeutralBorder
import com.example.foodpanda_capstone.view.ui.theme.NeutralDivider
import com.example.foodpanda_capstone.view.ui.theme.Typography
import com.google.android.material.search.SearchBar
import kotlinx.coroutines.launch
import org.w3c.dom.Text

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodpandaCapstoneTheme {
                val navController = rememberNavController()
                val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route ?: ""

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
                    windowInsets.isAppearanceLightStatusBars = statusBarBackgroundArgb ==  Color.White.toArgb()
                }

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
                    DrawerItems(Icons.Default.Share, "Share", 0, false),
                    DrawerItems(Icons.Filled.Star, "Rate", 0, false),
                    DrawerItems(Icons.Filled.Settings, "Setting", 0, false),
                    DrawerItems(Icons.Filled.MoreVert, "Terms & Conditions / Policy", 0, false),



                )

                var selectedItem by remember {
                    mutableStateOf(drawerItem[0])
                }

                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
//                val scope = rememberCoroutineScope()

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

                                    Text(
                                        text = "Mr User",
                                        Modifier
                                            .fillMaxWidth()
                                            .padding(top = 16.dp),
                                        fontSize = 22.sp,
                                        textAlign = TextAlign.Center
                                    )
                                }
                                Divider(
                                    Modifier.align(Alignment.BottomCenter), thickness = 1.dp,
                                    NeutralBorder
                                )

                            }
                            drawerItem.forEach{
                                NavigationDrawerItem(label = { Text(text = it.text) }
                                    , selected = it == selectedItem
                                    , onClick = {
                                        selectedItem = it

                                        scope.launch {
                                            drawerState.close()
                                        }

                                    },
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                    , icon = {
                                        Icon(modifier = Modifier, imageVector = it.icon, contentDescription = it.text,tint = BrandPrimary )
                                    }
                                    , badge = {
                                        if (it.hasBadge){
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
                            Divider( thickness = 1.dp,
                                color = Color.DarkGray
                            )
                            drawerItem2.forEach{
                                NavigationDrawerItem(label = { Text(text = it.text) }
                                    , selected = it == selectedItem
                                    , onClick = {
                                        selectedItem = it

                                        scope.launch {
                                            drawerState.close()
                                        }

                                    },
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                    , icon = {
                                        Icon(imageVector = it.icon, contentDescription = it.text, tint = BrandPrimary)
                                    }
                                )
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
                                                    SearchBar() { searchText ->
                                                        // Handle search functionality here (e.g., filter data based on searchText)
                                                        searchResult = "Searching for: $searchText"
                                                    }
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
                                        // NavigationDrawerM3
                                        if (isDrawerOpen) {

//                                        HomeAppBar()
//                                        DrawerHeader()
//                                        DrawerBody(items = listOf(
//                                            MenuItems(
//                                                id = "home",
//                                                title = "Home",
//                                                contentDescription = "Go to home screen",
//                                                icon = Icons.Default.Home
//                                            ),
//                                            MenuItems(
//                                                id = "settings",
//                                                title = "Settings",
//                                                contentDescription = "Go to settings screen",
//                                                icon = Icons.Default.Settings
//                                            ),
//                                            MenuItems(
//                                                id = "help",
//                                                title = "Help",
//                                                contentDescription = "Get help",
//                                                icon = Icons.Default.Info
//                                            ),
//                                        ), onItemClick = {
//                                            println("Clicked on ${it.title}")
//                                        }
//
//                                        )
                                        }
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
                                                Text(text = currentRoute, style = Typography.titleMedium)
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
                            "Home" -> {
                                HomeScreen(navController = navController)
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
                                        startDestination = "Home",
//                                    startDestination = "Playlist", // TODO: Update to Home page when home page is ready
                                    ) {

                                        composable("Home") {
                                            HomeScreen(navController)
                                        }
                                        composable("Playlist") {
                                            PlaylistScreen(navController)
                                        }
                                        composable("Playlist Form") {
                                            PlaylistFormScreen(navController)
                                        }

                                    }
                                }
                            }
                        }
                    }

                }

            }
        }
    }
}

//Sample search bar to filter database
@OptIn(ExperimentalComposeUiApi::class)
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



